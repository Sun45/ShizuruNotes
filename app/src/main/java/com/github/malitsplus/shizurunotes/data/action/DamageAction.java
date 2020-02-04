package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;

public class DamageAction extends ActionParameter {

    ClassModifier damageClass;
    CriticalModifier criticalModifier;

    @Override
    protected void childInit(){
        damageClass = ClassModifier.parse(actionDetail1);
        criticalModifier = CriticalModifier.parse((int)actionValue5);

        switch (damageClass){
            case magical:
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.magicStr));
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                break;
            case physical:
            case inevitablePhysical:
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.atk));
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                break;
            default:
        }
    }

    @Override
    public String localizedDetail(int level, Property property){
        StringBuilder string = new StringBuilder();
        switch (criticalModifier){
            case normal:
                string.append(I18N.getString(R.string.Deal_s_s_damage_to_s, buildExpression(level, property), damageClass.description(), targetParameter.buildTargetClause()));
                break;
            case critical:
                string.append(I18N.getString(R.string.Deal_s_s_damage_to_s_and_this_attack_is_ensured_critical, buildExpression(level, property), damageClass.description(), targetParameter.buildTargetClause()));
                break;
        }
        if(actionValue6 != 0){
            string.append(I18N.getString(R.string.Critical_damage_is_s_times_as_normal_damage, 2 * actionValue6));
        }

        return string.toString();
    }
}
