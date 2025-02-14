#!/bin/bash

gcc -o pizzeria pizzeria.c -pthread

./pizzeria 100 1000
