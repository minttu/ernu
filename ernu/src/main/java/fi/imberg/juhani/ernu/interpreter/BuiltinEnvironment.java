package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.*;

public class BuiltinEnvironment extends Environment {
    public BuiltinEnvironment() {
        super(null, "builtin");
        addSymbol("print", new PrintFunction());
        addSymbol("range", new RangeFunction());
        addSymbol("import", new ImportFunction());
        addSymbol("len", new LenFunction());
        addSymbol("help", new HelpFunction());
        addSymbol("type", new TypeFunction());
        addSymbol("defined", new DefinedFunction());
        addSymbol("apply", new ApplyFunction());
        addSymbol("namespace", new NamespaceFunction());
        addSymbol("use", new UseFunction());
        addSymbol("str", new StrFunction());
        addSymbol("num", new NumFunction());
    }
}
