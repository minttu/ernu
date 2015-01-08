package fi.imberg.juhani.ernu.repl;

public interface REPLUI {
    public void comment(String string);

    public void display(String string);

    public void error(String string);

    public void ok(boolean bool);

    public void raw(int b);

    public String read();

    public boolean ready();
}
