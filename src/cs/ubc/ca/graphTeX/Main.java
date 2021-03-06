package cs.ubc.ca.graphTeX;

import cs.ubc.ca.graphTeX.ast.AST;
import cs.ubc.ca.graphTeX.tokenize.Tokenizer;
import cs.ubc.ca.graphTeX.util.IndentingPrinter;
import cs.ubc.ca.graphTeX.visitor.EvaluateVisitor;
import cs.ubc.ca.graphTeX.visitor.Parser;
import cs.ubc.ca.graphTeX.visitor.PrintVisitor;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> literals = Arrays.asList("graph","nodes","[","]", "->", "<-", "--", "<>", "(", ")", ",", "{", "}", "*", ":", "tree", "loop", "makeall", "withvalue", "colour");
        Tokenizer.makeTokenizer("input.gtex",literals);

        Parser parser = new Parser();
        AST ast = parser.parse();

        EvaluateVisitor v = new EvaluateVisitor();
        System.out.println(ast.accept(v));

      /*  System.out.println("Parsed successfully");

        PrintVisitor indentingPrinter = new PrintVisitor(new IndentingPrinter(System.out));
        ast.accept(indentingPrinter);*/
    }
}
