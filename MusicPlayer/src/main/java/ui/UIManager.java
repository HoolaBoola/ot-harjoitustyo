package ui;


public class UIManager {
    
    private UI ui;
    public UIManager(UI ui) {
        this.ui = ui;
    }
    
    public void start() {
        ui.startApplication();
    }

}
