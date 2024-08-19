package com.xxxx.crm.proxy;

//@Component
//@Aspect
//public class PermissionProxy {
//    @Autowired
//    private HttpSession session;
//    @Around(value = "@annotation(com.xxxx.crm.annotaions.RequirePermission)")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        List<String> permissions = (List<String>) session.getAttribute("permissions");
//        if(null == permissions || permissions.size()==0){
//            throw new NoLoginException();
//        }
//        Object result =null;MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//        RequirePermission requirePermission =
//                methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
//        if(!(permissions.contains(requirePermission.code()))){
//            throw new NoLoginException();
//        }
//        result= pjp.proceed();
//        return result;
//    }
//}