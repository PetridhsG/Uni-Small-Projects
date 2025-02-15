# Electronic Movie Library (C++ - SGG)

This project is a graphical **Electronic Movie Library** implemented in **C++** using the **Simple Graphics Library (SGG)**. The application allows users to browse, filter, and search for movies based on various attributes.

## Overview

The goal of this project is to develop a **graphical user interface (GUI)** for managing a movie collection. The application allows users to search for movies using different filters while implementing **inheritance, polymorphism, and STL data structures** efficiently.

### Required Movie Attributes:
- **Title**  
- **Release Year**  
- **Director(s)**  
- **Main Actors**  
- **Genre(s)**  

Example:  
*"Schindler's List", 1993, "Steven Spielberg", "Liam Neeson, Ralph Fiennes, Ben Kingsley", "Drama, History"*

Additional details such as **plot summary** or **poster images** can be included.

## Features

✅ **Graphical Movie Library using SGG**  
✅ **Search & Filter Movies** (by release year, genre)  
✅ **Custom Graphical Elements** (buttons, sliders, checkboxes)  
✅ **Polymorphic Widget System**  
✅ **Efficient Use of STL Containers**  
✅ **Free-text Search (Title, Director, Actors)**  
✅ **Advanced UI Features (Animations, Additional Widgets, etc.)**  

# Movie Search Application

## Usage

Once the program starts:

1. **Search Bar**: 
   - Use the search bar to enter a **title**, **director**, or **actor**.
   
2. **Filters**: 
   - Apply filters to narrow down results by **year** or **genre**.
   
3. **Movie Details**: 
   - Click on a movie entry to view its details.
   
4. **Carousel**: 
   - Load **poster images** and display additional information about the movie.

---

## Implementation Details

The project follows an **Object-Oriented Design (OOD)** with a hierarchy of classes. The primary features include:

### Core Concepts:

- **Widget Class**: 
   - A base class that ensures **code reusability** and **polymorphic method calls**.
   
- **STL (Standard Template Library)**:
   - Used for **fast searching** and **filtering** of movie data.
   
- **SGG Graphics Functions**:
   - Used for **UI rendering** and graphical interface components.

- **Thread Safety**: 
   - Necessary thread safety considerations have been implemented where needed to ensure stability and reliability.

---

## Graphical User Interface

The UI consists of the following components:

1. **Movie List Panel**: 
   - Displays a list of available movies based on search/filter results.
   
2. **Search Bar**: 
   - A text input field to search by title, director, or actor.
   
3. **Filter Dropdowns**:
   - Dropdown menus for filtering movies by **year** and **genre**.
   
4. **Movie Details Section**:
   - Displays detailed information for a selected movie.
   
5. **Interactive Buttons & Checkboxes**: 
   - Buttons for interaction, such as loading posters and toggling additional information.

