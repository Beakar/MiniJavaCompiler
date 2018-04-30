Set up your project in a manner similar to the previous projects:
  - dragging the src folders in the starter project onto the src icon
    in the package explorer
  - dragging the jars and lib folders, as well as the .java test files and
    .asm library files, onto the (top-level) project folder in the package
    explorer. (You will also want to create your own additional .java
    test files.)
  - adding the lib folder as a class folder for the project, and by adding
    the WrangLR jar-file (inside the jars folder) as a jar file for the
    project.

To run your MiniJava compiler and then run the resulting code in the MIPS
simulator:
  - Set up to run the main/Main5 as an application, with the command-line
    arguments as follows:
        Abc.java -o Abc.asm -a mjLib.asm
    - you may optionally use the -w or -p switch in order to view the AST
    - if you want the "annotating" version of the MiniJava library use the
      arguments
        Abc.java -o Abc.asm -a mjLibAnn.asm
  - At this point, you may need to click on your top-level project icon
    and refresh its file list (with the F5 key).  Your Abc.asm file should
    be visible.
  - run your Abc.asm file on the MIPS simulator:
    - drag your Abc.asm file to a folder containing the MIPS simulator (mips.bat
    and mips.jar, or similarly named jar file).
    - on a PC, drag the new copy of your Abc.asm file onto the mips.bat icon.
    - on a Mac (or if this does not work on a PC), double-click on MIPS
      jar-file, and type in the name of your file, Abc.asm
    - the simulator should start running.

The starter file project will:
  - produce no vtables
  - produce exacly one assembly-level image for each string literal in the
    source program. (It will not produce exactly one image for every UNIQUE
    string literal, as it should.)
  - produce no executable code.
You will therefore not be able to successfully assemble code that the
starter-file produces.
