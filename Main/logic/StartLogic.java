package Main.logic;

import Main.UI.RuleUI;
import Main.UI.StartUI;

public class StartLogic {
    private StartUI startui;
    private RuleUI ruleui;
    public void setStartUI(StartUI k){
        startui = k;
    }
    public void setRuleUI(RuleUI k){
        ruleui = k;
    }    
    public void operationButton(String cmd){
        if (cmd.equals("start")){
            startui.setVisible(false);
        }else {
            ruleui.setVisible(true);
        }
    }
}
