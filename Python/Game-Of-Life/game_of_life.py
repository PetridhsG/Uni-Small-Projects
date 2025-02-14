"""Conway's Game of Life."""

from re import S

def board(n):
    """Constructor of a game board.

    n -- matrix dimension parameters

    Constructs a representation of a game board with n x n cells, where
    initially, no cell is alive.

    The board is represented as a dictionary (dict) with n * n elements.
    Each cell corresponds to an element with a key (tuple) (i, j),
    where i is the row number and j is the column number of the cell.
    (The numbering of rows and columns is from 0 to n-1. The top-left cell
    is located at (0,0).)
    The value of the element is True or False, depending on whether 
    the cell is alive or not.

    Examples:

    >>> game = board(3)
    >>> len(game)
    9
    >>> game
    {(0, 0): False, (0, 1): False, (0, 2): False, (1, 0): False, (1, 1): False, (1, 2): False, (2, 0): False, (2, 1): False, (2, 2): False}
    >>> game[2,1]
    False
    """

    dic = {}
    for i in range(n):
        for j in range(n):
            x = (i,j)
            dic[x] = False
    return dic


def is_alive(board, p):
    """Check if a cell is alive.

    board -- the game board where the cell is located
    p -- the cell's position.

    The parameter p is a tuple in the form (i, j).
    Returns True if the cell is alive, otherwise False.

    Example:

    >>> is_alive(board(4), (3,2))
    False
    """

    return board[p]

def set_alive(board, p, alive):
    """Create or remove life from a cell.

    board -- the game board where the cell is located
    p -- the cell's position (a tuple in the form (i, j))
    alive -- a boolean value.

    The cell becomes alive if alive is True. If alive is False, the cell dies.

    Example:

    >>> game = board(4)
    >>> is_alive(game, (3,2))
    False
    >>> set_alive(game, (3,2), True)
    >>> is_alive(game, (3,2))
    True
    >>> set_alive(game, (3,2), False)
    >>> is_alive(game, (3,2))
    False
    """

    if alive == True :
        board[p] = True
    else:
        board[p] = False

def get_size(board):
    """Size of the game board.

    board -- game board.

    Returns n (an integer) if the board represents an n x n game board.

    Example:

    >>> get_size(board(4))
    4
    >>> get_size(board(10))
    10
    """

    return int(len(board)**(1/2))


def copy_board(board):
    """Create a copy of the game board.

    board -- game board.

    Returns a new game board that is a copy of the board parameter.

    Example:

    >>> game = board(10)
    >>> set_alive(game, (4,7), True)
    >>> game2 = copy_board(game)
    >>> game2 is game
    False
    >>> is_alive(game2, (4,7))
    True
    """
 
    return board.copy()


def get_iterator(board):
    """Iterator for iterating through game board cells.

    board -- game board.

    Returns an iterator that provides the cells of the board, starting from
    the cells of the first row: (0,0), (0,1), (0,2), ..., and then the cells
    of the second row: (1,0), (1,1), (1,2), ..., and so on until all cells
    are visited. For each cell, the iterator provides its position and a
    boolean value True or False depending on whether it is alive or not.

    Example:

    >>> game = board(3)
    >>> set_alive(game, (2, 1), True)
    >>> for cell in get_iterator(game):
    ...     print(cell)
    ... 
    ((0, 0), False)
    ((0, 1), False)
    ((0, 2), False)
    ((1, 0), False)
    ((1, 1), False)
    ((1, 2), False)
    ((2, 0), False)
    ((2, 1), True)
    ((2, 2), False)
    """

    t = list(board)
    ls = []
    for i in range(get_size(board)):
        for j in range(get_size(board)):
            if t[i][0] == i :
                ls = ls + [(i,j)]
            else :
                ls = ls + [(i,j)]
    di = {}
    for i in range(len(ls)):
        di[ls[i]] = board[ls[i]]
    x = di.items()
    return iter(x)


def print_board(board):
    """Display the game board.

    board -- game board.

    Displays the game board using a black square (unicode character 11035)
    for live cells, and a white square (unicode character 11036) for dead
    cells. The top-left cell is in position (0,0).

    Example:

    >>> game = board(5)
    >>> set_alive(game, (0,0), True)
    >>> set_alive(game, (2,2), True)
    >>> set_alive(game, (4,4), True)
    >>> set_alive(game, (3,4), True)
    >>> set_alive(game, (0,4), True)
    >>> print_board(game)
    ⬛⬜⬜⬜⬛
    ⬜⬜⬜⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    ⬜⬜⬜⬜⬛
    """
    
    for i in range(get_size(board)):
        ls = ""
        for j in range(get_size(board)):
            if board[(i,j)] == True :
                ls = ls + chr(11035)
            else:
                ls = ls + chr(11036)
        print(ls)


def neighbors(p):
    """Neighbor cells.

    p -- cell position (a tuple in the form (i, j)).

    Returns a set that contains the positions of the 8 neighboring cells
    of p. The cell p is not included in the set.

    Example:

    >>> neighbors((3,2)) == {(3, 3), (3, 1), (2, 1), (2, 3), (4, 3), (2, 2), (4, 2), (4, 1)}
    True
    >>> neighbors((0,0)) == {(0, 1), (-1, 1), (-1, 0), (-1, -1), (0, -1), (1, 0), (1, -1), (1, 1)}
    True
    >>> neighbors((0,10)) == {(-1, 9), (1, 10), (-1, 11), (0, 11), (-1, 10), (1, 9), (0, 9), (1, 11)}
    True
    """

    s = {}
    s = set(s)
    s.add((p[0],p[1] - 1))
    s.add((p[0],p[1] + 1))
    s.add((p[0]-1,p[1]-1))
    s.add((p[0]-1,p[1]))
    s.add((p[0]-1,p[1]+1))
    s.add((p[0]+1,p[1]-1))
    s.add((p[0]+1,p[1]))
    s.add((p[0]+1,p[1]+1))
    return s


def place_blinker(board, p = (0,0)):
    """Place a blinker on the game board.

    board -- game board.
    p -- cell position (a tuple (i, j) with a default value of (0,0)).

    Places 3 live cells on the board in a vertical line starting from
    the position p, as shown in the examples. Note that the cell at position p
    itself is not initially alive.

    Example:

    >>> game = board(5)
    >>> place_blinker(game)
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (1,2))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (4,4))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    """

    set_alive(board,(p[0],p[1]),True)
    set_alive(board,(p[0]+1,p[1]),True)
    set_alive(board,(p[0]+2,p[1]),True)

def place_glider(board, p = (0,0)):
    """Place a glider on the game board.

    board -- game board.
    p -- cell position (a tuple (i, j) with a default value of (0,0)).

    Places 5 live cells on the board in the shape of a glider starting from
    the position p, as shown in the examples. Note that the cell at position p
    itself is not initially alive.

    Example:

    >>> game = board(7)
    >>> place_glider(game)
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    >>> place_glider(game, (3,3))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬛⬜⬛⬜
    ⬜⬜⬜⬜⬛⬛⬜
    ⬜⬜⬜⬜⬜⬜⬜
    """

    set_alive(board,(p[0],p[1]+2),True)
    set_alive(board,(p[0]+1,p[1]),True)
    set_alive(board,(p[0]+1,p[1]+2),True)
    set_alive(board,(p[0]+2,p[1]+1),True)
    set_alive(board,(p[0]+2,p[1]+2),True)


def tick(board):
    """Progress the game by one generation.

    board -- game board.

    Updates the board to the next generation according to the rules
    of Conway's Game of Life.

    Example:

    >>> game = board(6)
    >>> place_glider(game)
    >>> place_blinker(game, (3,4))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    >>> tick(game)
    >>> print_board(game)
    ⬜⬛⬜⬜⬜⬜
    ⬜⬜⬛⬛⬜⬜
    ⬜⬛⬛⬛⬜⬜
    ⬜⬜⬜⬛⬜⬜
    ⬜⬜⬜⬛⬛⬛
    ⬜⬜⬜⬜⬜⬜
    """
    
    copy = copy_board(board)
    for i in range(get_size(board)):
         for j in range(get_size(board)):
            lst = neighbors((i,j))
            lst = list(lst)
            ls = []
            for t in range(len(lst)):
                p = lst[t]
                if p[0] >= 0 and p[1] >= 0 and p[0] < get_size(board) and p[1] < get_size(board) :
                    ls = ls + [p]
            n = 0
            for t in range(len(ls)):
                    if copy[ls[t]] == True :
                        n += 1
            if is_alive(copy,(i,j)) == False:
                if n == 3 :
                    set_alive(board,(i,j),True)
            else:
                if n == 0 or n == 1 :
                    set_alive(board,(i,j),False)
                elif n == 2 or n == 3 :
                    set_alive(board,(i,j),True)
                else :
                    set_alive(board,(i,j),False)


if __name__ == '__main__':
    """Play the game of life for 100 generations.

    generations -- number of generations to play.

    Play the game for a specific initial configuration for 100 generations.
    The game board is displayed at each generation. At least 2 blank lines
    are left between consecutive boards.
    """
  
    game = board(10)
    place_blinker(game, (1,2))
    place_glider(game, (2,4))

    from time import sleep

    for i in range(100):
        print_board(game)
        print(" ")
        tick(game)
        sleep(0.4)


