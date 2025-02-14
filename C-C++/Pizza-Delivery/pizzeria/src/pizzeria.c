
#include "config.h"
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <sys/time.h>

int Ncust ;         // customers
int seed ;          // seed

int income = 0  ;        // total income
int Nplain = 0 ;         // number of plain pizzas
int Nspecial = 0 ;       // number of special pizzas
int Nsuccessful = 0 ;    // number of successful orders
int Nunsuccessful = 0 ;  // number of unsuccessful orders

float Avg_serving_time;  // average time of serving
float max_serving_time;  // maximum time of serving

float Avg_cold;       // average colding time
float max_cold;       // maximum colding time

int free_cooks = Ncook ;         // number of available cooks
pthread_mutex_t cooks_mutex ;
pthread_cond_t cooks_cond ;

int free_ovens = Noven ;    // number of available ovens
pthread_mutex_t ovens_mutex ;
pthread_cond_t ovens_cond ;


int free_packers = Npacker ;    // number of available packers
pthread_mutex_t packers_mutex ;
pthread_cond_t packers_cond ;


int free_deliverers = Ndeliverer ;    // number of available deliverers
pthread_mutex_t deliverers_mutex ;
pthread_cond_t deliverers_cond ;

pthread_mutex_t screen_lock ;   // screen lock 

pthread_mutex_t payment_mutex;  // payment lock

pthread_mutex_t stats_mutex;    // statistics lock



void * order(void * arg){
	struct timespec cook_start,cook_finish,oven_start,oven_finish,      // structures for clock_gettime function
	packer_start,packer_finish,deliverer_start,deliverer_finish;
    int rand;                   // random number
    int id = *(int *) arg;      // id of customer
    int connection_time = rand_r(&seed) % Torderhigh + Torderlow ; // random connection time
    int pizzas = rand_r(&seed) % Norderhigh + Norderlow ;   // random number of pizzas
    int plains = 0 ; 
    int specials = 0 ;
    for (int i = 0 ; i < pizzas ; i++){
        rand = rand_r(&seed) % 100 + 1 ;
        if (rand <= Pplain){                // if rand is less than 60 (probability of 60%)
            plains++;
        }
        else{
            specials++;
        }
    }
    
    int payment_time = rand_r(&seed) % Tpaymenthigh + Tpaymentlow; // random payment time
    rand = rand_r(&seed) % 100 + 1 ;
    
    if (rand <= Pfail){                         // if rand is less than 10 (probability of 10%)
        pthread_mutex_lock(&screen_lock);
        printf("Order %d was unsuccessful.\n",id);
        pthread_mutex_unlock(&screen_lock);
        pthread_mutex_lock(&payment_mutex); 
        Nunsuccessful++;
        pthread_mutex_unlock(&payment_mutex);
        pthread_exit(-1);                       // if the payment failed exit the thread
    }
    else{
        pthread_mutex_lock(&screen_lock);
        printf("Order %d was successful.\n",id);
        pthread_mutex_unlock(&screen_lock);
        pthread_mutex_lock(&payment_mutex);
        Nspecial += specials;
        Nplain += plains;
        Nsuccessful++;
        income += plains*Cplain + specials*Cspecial ;
        pthread_mutex_unlock(&payment_mutex);
    }
    
    	
	if (Nsuccessful == 1){			// first customer at 0
		connection_time = 0 ;
	}
	
    pthread_mutex_lock(&cooks_mutex);               
    clock_gettime(CLOCK_REALTIME,&cook_start);          // start counting time of preparation
    
    while(free_cooks == 0){             
        pthread_cond_wait(&cooks_cond, &cooks_mutex);   // wait untill there is a cook available
    }
    
    free_cooks--;
    pthread_mutex_unlock(&cooks_mutex);

    for(int i = 0 ; i < pizzas ; i++){          // time of preparation for every pizza 
        sleep(Tprep);
    }
   
    pthread_mutex_lock(&ovens_mutex);
    clock_gettime(CLOCK_REALTIME,&oven_start);          // start counting time of baking
    
    while(free_ovens < pizzas ){
        pthread_cond_wait(&ovens_cond, &ovens_mutex);   // wait untill every oven needed is available
    }
   
    pthread_mutex_lock(&cooks_mutex);
    free_cooks++;
    pthread_cond_signal(&cooks_cond);   
    clock_gettime(CLOCK_REALTIME,&cook_finish);         // stop counting time of preparation
    pthread_mutex_unlock(&cooks_mutex);
    
    float cook_time = (cook_finish.tv_sec - cook_start.tv_sec);     // time of preparation

    free_ovens -= pizzas;
    pthread_mutex_unlock(&ovens_mutex);

    sleep(Tbake);           // time of baking 

    pthread_mutex_lock(&packers_mutex);
    clock_gettime(CLOCK_REALTIME,&packer_start);        // start counting time of packing
    
    while(free_packers == 0 ){
        pthread_cond_wait(&packers_cond, &packers_mutex);       // wait untill a packer is available
    }

    free_packers--;
    pthread_mutex_unlock(&packers_mutex);

    for(int i=0 ; i < pizzas ; i++){
        sleep(Tpack);                           // time of packeting for every pizza
    }

    pthread_mutex_lock(&ovens_mutex);
    free_ovens +=pizzas;
    pthread_cond_signal(&ovens_cond);
    clock_gettime(CLOCK_REALTIME,&oven_finish);     // stop counting time of baking
    pthread_mutex_unlock(&ovens_mutex);
    
    float oven_time = (oven_finish.tv_sec - oven_start.tv_sec);      // time of baking

    pthread_mutex_lock(&packers_mutex);
    free_packers++;
    pthread_cond_signal(&packers_cond);
    clock_gettime(CLOCK_REALTIME,&packer_finish);           // stop counting time of packeting
    pthread_mutex_unlock(&packers_mutex);

	float packer_time = (packer_finish.tv_sec - packer_start.tv_sec);        // time of packeting
	
    float packeting_time = cook_time + oven_time + packer_time + payment_time - connection_time;        // total time of preparation

    pthread_mutex_lock(&screen_lock);
    printf("Order %d prepared in %.2f minutes.\n",id,packeting_time / 60.0);
    pthread_mutex_unlock(&screen_lock);


    pthread_mutex_lock(&deliverers_mutex);
    clock_gettime(CLOCK_REALTIME,&deliverer_start);             // start counting time of delivering
    while(free_deliverers == 0 ){
        pthread_cond_wait(&deliverers_cond, &deliverers_mutex);     // wait untill a deliverer is available
    }

    free_deliverers--;
    pthread_mutex_unlock(&deliverers_mutex);

    rand = rand_r(&seed) % (Tdelhigh - 4) + Tdellow;

    sleep(rand);        // time of delivering

	clock_gettime(CLOCK_REALTIME,&deliverer_finish);        // stop counting time of deliverin

	
	float deliverer_time = (deliverer_finish.tv_sec - deliverer_start.tv_sec);      // delivering time 
    
    float delivering_time = deliverer_time + packeting_time + payment_time - connection_time;    // total time from the preparing to delivering
    
	pthread_mutex_lock(&screen_lock);
    printf("Order %d delivered in %.2f minutes.\n",id,delivering_time / 60.0);
   	pthread_mutex_unlock(&screen_lock);
   	
   	sleep(rand);                // time for a deliverer to return
   	
    pthread_mutex_lock(&deliverers_mutex);
    free_deliverers++;
    pthread_cond_signal(&deliverers_cond);
    pthread_mutex_unlock(&deliverers_mutex);
    

     	
   	pthread_mutex_lock(&stats_mutex);
   	Avg_serving_time += delivering_time;
   	
   	if ( delivering_time > max_serving_time ){
   		max_serving_time  = delivering_time;
   	}
  
   	
   	int cold = delivering_time - oven_time - cook_time ;
   	Avg_cold += cold ;
   	
   	if ( cold > max_cold ){
   		max_cold = cold;
   	}
   	pthread_mutex_unlock(&stats_mutex);
   

    free(arg);
    pthread_exit(NULL);
}



int main(int argc, char* argv[]) {

    if (argc != 3){
        printf("Wrong number of arguments given!");         
        exit(-1);
    }

    const Ncust = atoi(argv[1]);
    if (Ncust <= 0 ){
        printf("Wrong number of customers given!");
        exit(-1);
    }

    seed = atoi(argv[2]);
    Avg_serving_time = 0 ;
    max_serving_time = 0 ;
    Avg_cold = 0 ;
    max_cold = 0 ;

    pthread_t th[Ncust];    // order threads

    //initialize all threads
    pthread_mutex_init(&cooks_mutex,NULL);  
    pthread_mutex_init(&ovens_mutex,NULL);
    pthread_mutex_init(&packers_mutex,NULL);
    pthread_mutex_init(&deliverers_mutex,NULL);
    pthread_mutex_init(&screen_lock,NULL);
    pthread_mutex_init(&payment_mutex,NULL);
    pthread_mutex_init(&stats_mutex,NULL);

    //initialize all condition variables
    pthread_cond_init(&cooks_cond, NULL);
    pthread_cond_init(&ovens_cond, NULL);
    pthread_cond_init(&packers_cond, NULL);
    pthread_cond_init(&deliverers_cond, NULL);

    //create all threads
    for(int i = 0 ; i < Ncust ; i++){
        int *a = malloc(sizeof(int));      // allocate memory for the thread argument
        *a = i + 1;                        // thread argument (order id)
        if (pthread_create(&th[i],NULL,&order,a)){     
            return 0;
        }
    }
    
    //join all threads
    for (int i =0 ; i < Ncust; i++){
        if(pthread_join(th[i],NULL) != 0){     
            return 0;
        }
    }
    
    //destroy all threads
    pthread_mutex_destroy(&cooks_mutex);
    pthread_mutex_destroy(&ovens_mutex);
    pthread_mutex_destroy(&packers_mutex);
    pthread_mutex_destroy(&deliverers_mutex);
    pthread_mutex_destroy(&screen_lock);
    pthread_mutex_destroy(&payment_mutex);
    pthread_mutex_destroy(&stats_mutex);

    //destroy all condition variables
    pthread_cond_destroy(&cooks_cond);
    pthread_cond_destroy(&ovens_cond);
    pthread_cond_destroy(&packers_cond);
    pthread_cond_destroy(&deliverers_cond);
    
    Avg_serving_time = Avg_serving_time / Nsuccessful;
    Avg_cold = Avg_cold / Nsuccessful ;
    //print all the statisticts
    printf("Total income:%d \n",income);
    printf("Number of unsuccessful orders:%d \n",Nunsuccessful);
    printf("Number of successful orders:%d \n",Nsuccessful);
    printf("Number of plains:%d \n",Nplain);
    printf("Number of specials:%d \n",Nspecial);
    printf("Average serving time:%.2f minutes \n",Avg_serving_time / 60.0);
    printf("Maximum serving time:%.2f minutes \n",max_serving_time / 60.0);
    printf("Average colding time:%.2f minutes \n",Avg_cold / 60.0);
    printf("Maximum colding time:%.2f minutes \n",max_cold / 60.0);
    
    return 0; 
}
