package UET.security.rbac.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicPolicyEnforcement implements PolicyEnforcement {
    private static final Logger logger = LoggerFactory.getLogger(BasicPolicyEnforcement.class);

    @Autowired
    @Qualifier("jsonFilePolicyDefinition")
    private PolicyDefinition policyDefinition;

    @Override
    public boolean check(Object subject, Object resource, Object condition, Object environment) {
        ExpressionParser exp = new SpelExpressionParser();
        PolicyRule rule = new PolicyRule("", "", exp.parseExpression("true"), exp.parseExpression(condition.toString()));
        SecurityAccessContext cxt = new SecurityAccessContext(subject, resource, null, environment);
        return checkRules(rule, cxt);
    }

    private boolean checkRules(PolicyRule rule, SecurityAccessContext cxt) {
            try {
                if (rule.getCondition().getValue(cxt, Boolean.class)) {
                    return true;
                }
            } catch (EvaluationException ex) {
                logger.info("An error occurred while evaluating PolicyRule.", ex);
            }
        return false;
    }
}
