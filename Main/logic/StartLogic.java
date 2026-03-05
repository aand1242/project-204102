package Main.logic;

import Main.UI.RuleUI;
import Main.UI.StartUI;

public class StartLogic {
    private StartUI startui;
    private RuleUI ruleui;
    private GameControl gameControl;
    public void setStartUI(StartUI k){
        startui = k;
    }
    public void setRuleUI(RuleUI k){
        ruleui = k;
    }
    public void setGamecontrol(GameControl g){
        gameControl = g;
    }
    public void operationButton(String cmd){
        if (cmd.equals("start")){
            startui.setVisible(false);
            gameControl.resetGame();
            // System.out.print("reset");
        }else {
            ruleui.setVisible(true);
        }
    }
}
