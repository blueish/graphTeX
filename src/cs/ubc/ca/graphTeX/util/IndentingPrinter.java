package cs.ubc.ca.graphTeX.util;

import java.io.*;

public class IndentingPrinter {
    private PrintStream printer;
    private int indentLevel = 0;

    public IndentingPrinter(PrintStream out) {
        this.printer = out;
    }

    private void print(String string) {
        for (int i = 0; i < indentLevel; i++) {
            printer.print("    ");
        }
        printer.print(string);
    }


    public void println(Object obj) {
        print(obj.toString());
        printer.println();
    }

    public void indent() {
        indentLevel++;
    }

    public void outdent() {
        indentLevel--;
    }
}
