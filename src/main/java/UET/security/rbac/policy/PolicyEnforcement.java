package UET.security.rbac.policy;

public interface PolicyEnforcement {

	boolean check(Object subject, Object resource, Object action, Object environment);
}
