package com.group.realm;


import com.group.dao.UserMapper;
import com.group.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Permissions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName MyRealm
 * @Description 自定义的reaml类   实现用户认证和授权
 * @Author ss
 * @Date 2020/7/28 10:56
 * @Version 1.0
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //System.out.println("进入授权方法了！！！");
        //测试授权
        //1.通过 principals获取用户信息
       String principal =(String) principals.getPrimaryPrincipal();//得到用户名
        //2.给用户添加角色(以set集合方法添加),可能会通过数据库查询权限
        /*Set<String> roles = new HashSet<String>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }*/
        Set<String> roles = (Set<String>) SecurityUtils.getSubject().getSession().getAttribute("roles");
       //查询数据库得到用户的权限
      /*  List<Permissions> permissionsList = prm.queryPermissionsByPersonName(principal);
        for (Permissions p: permissionsList) {
           roles.add(p.getPermissionsName());
        }*/
        //3.返回new SimpleAuthorizationInfo(set集合的引用);
        return new SimpleAuthorizationInfo(roles);
    }
    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //System.out.println("-------我进来了--------");
        //1.将token强转为UsernamePasswordToken类型（可以通过这个类型可以拿到身份(用户名)）
        UsernamePasswordToken token1 = (UsernamePasswordToken)token;
        //System.out.println(token1.getUsername());
        System.out.println(token1.getUsername());
        User p = userMapper.queryPersonByPersonName(token1.getUsername());
        System.out.println(p);
        //System.out.println(p);
        if(p!=null){
            //2.设置shiro比对器身份
            Object principal = p.getUsername();
            //3.设置比对器里面的密码(把数据库里面的密码作为比对密码)
            Object credentials = p.getPassword();
            //4.自动给令牌类里面的用户名设置加盐方式
           ByteSource salt= ByteSource.Util.bytes(p.getUsername());
            //5.设置realm的名称
            String realmName = this.getName();
            //new SimpleAuthenticationInfo(principal,credentials,salt,realmName)才是真正的认证

          /* 如何认证：
           将上面的principal和token1里面的用户名来比对
           将上面的credentials和token1里面的密码来比对
           因为spring-shiro.xml里面配置加密方式 所以会自动把token1里面的密码以MD5加密，加密1024次
          因为上面传了个salt，表示再把token1里面的密码再以salt加盐
           * */
           return  new SimpleAuthenticationInfo(principal,credentials,salt,realmName);
        }else{
            throw  new AuthenticationException();
        }


    }

  /*  public static void main(String[] args) {
        //加密测试代码
        //设置加密方式
        String algorithmName="MD5";
        //设置待加密的原密码
        Object source="123";
        //设置加盐方式(一般来说都是以用户名来加盐)
        Object salt= ByteSource.Util.bytes("user");
        //加密次数
        int hashIterations=1024;
        SimpleHash newPassword=new SimpleHash(algorithmName, source, salt, hashIterations);
        System.out.println(newPassword.toString());
    }*/
}
