package patterns.behavioral.command;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * “Com­mand is a behav­ioral design pat­tern that turns a request into a 
 * stand-alone object that con­tains all infor­ma­tion about the request. This 
 * trans­for­ma­tion lets you pass requests as a method argu­ments, delay or queue a
 *  request’s exe­cu­tion, and sup­port undoable operations.”
 * 
 * “Use the Com­mand pat­tern when you want to param­e­trize objects with operations.”
 * 
 * “Use the Com­mand pat­tern when you want to queue oper­a­tions, sched­ule their 
 * exe­cu­tion, or exe­cute them remotely.”
 * 
 * “Use the Com­mand pat­tern when you want to imple­ment reversible operations.”
 * 
 * Excerpt From: Alexander Shvets. “Dive Into Design Patterns”. Apple Books. 
 */
public interface Command<T> {
    
    /**
     * Executes request argu­ments along with a ref­er­ence to the actu­al receiv­er 
     * object.
     */
    public void execute();

    /**
     * Sets the actual receiver. Useful for remote execution.
     * @param receiver
     */
    public void setReceiver(T receiver);
}

class Test {

    public static void main(String[] args) {
        Application application = new Application();

        application.createUI();

        application.getShortcuts().press("Ctrl+C");
        application.getCopyButton().execute();

        application.getShortcuts().press("Ctrl+Z");

        System.out.println();
    }
}

class Shortcuts {

    private HashMap<String, Invoker> keys = new HashMap<>();

    public void set(String key, ApplicationCommand command) {
        Invoker invoker = new Invoker();
        invoker.set(command);

        this.keys.put(key, invoker);
    }

    public void press(String key) {
        Invoker shortcut = this.keys.get(key);

        if (shortcut == null) {
            return;
        }

        shortcut.execute();
    }
}

class Application {

    private String clipboard;
    private ArrayList<ApplicationCommand> history = new ArrayList<>();
    private Editor editor = new Editor();
    private Shortcuts shortcuts = new Shortcuts();
    private Invoker copyButton = new Invoker();
    private Invoker pasteButton = new Invoker();
    private Invoker cutButton = new Invoker();
    private Invoker undoButton = new Invoker();

    public void createUI() {
        Copy copy = new Copy(this, editor);
        Paste paste = new Paste(this, editor);
        Cut cut = new Cut(this, editor);
        Undo undo = new Undo(this, editor);

        shortcuts.set("Ctrl+C", copy);
        shortcuts.set("Ctrl+V", paste);
        shortcuts.set("Ctrl+X", cut);
        shortcuts.set("Ctrl+Z", undo);

        copyButton.set(copy);
        pasteButton.set(paste);
        cutButton.set(cut);
        undoButton.set(undo);
    }

    public Shortcuts getShortcuts() {
        return this.shortcuts;
    }

    public Invoker getCopyButton() {
        return this.copyButton;
    }

    public String getClipboard() {
        return this.clipboard;
    }

    public void setClipboard(String text) {
        this.clipboard = text;
    }

    public void addApplicationCommand(ApplicationCommand command) {
        this.history.add(command);
    }

    public void removeApplicationCommand(ApplicationCommand command) {
        this.history.remove(command);
    }
}

class Editor {
    private String text = "Hello World from Editor!";

    public String getSelection() {
        return this.text;
    }

    public void replaceSelection(String text) {
        this.text = text;
    }
}

interface ApplicationCommand extends Command<Application> {

    public void undo();
}

class Copy implements ApplicationCommand {

    private Application application;
    private Editor editor;
    private String clipboardBackup;

    public Copy(Application application, Editor editor) {
        this.application = application;
        this.editor = editor;
    }

    @Override
    public void execute() {
        String text = this.editor.getSelection();
        this.clipboardBackup = this.application.getClipboard();
        this.application.setClipboard(text);
        this.application.addApplicationCommand(this);
    }

    @Override
    public void setReceiver(Application receiver) {
        this.application = receiver;
    }

    @Override
    public void undo() {
        this.application.setClipboard(this.clipboardBackup);
        this.application.removeApplicationCommand(this);
    }
}

class Cut implements ApplicationCommand {

    private Application application;
    private Editor editor;

    public Cut(Application application, Editor editor) {
        this.application = application;
        this.editor = editor;
    }

    @Override
    public void execute() {
        String text = this.editor.getSelection();
        this.application.setClipboard(text);
        this.editor.replaceSelection("");
        this.application.addApplicationCommand(this);
    }

    @Override
    public void setReceiver(Application receiver) {
        this.application = receiver;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}

class Paste implements ApplicationCommand {

    private Application application;
    private Editor editor;

    public Paste(Application application, Editor editor) {
        this.application = application;
        this.editor = editor;
    }

    @Override
    public void execute() {
        String text = this.application.getClipboard();
        this.editor.replaceSelection(text);
        this.application.setClipboard("");
        this.application.addApplicationCommand(this);
    }

    @Override
    public void setReceiver(Application receiver) {
        this.application = receiver;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}

class Undo implements ApplicationCommand {

    private Application application;
    private Editor editor;

    public Undo(Application application, Editor editor) {
        this.application = application;
        this.editor = editor;
    }

    @Override
    public void execute() {
    }

    @Override
    public void setReceiver(Application receiver) {
        this.application = receiver;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}