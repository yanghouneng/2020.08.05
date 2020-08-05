package com.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.dao.UserMapper;
import com.group.pojo.User;
import com.group.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper pm;
    @Override
    public String checkLogin(User p, String remember, HttpServletResponse response, HttpServletRequest request) {
        //1.拿到当前用户
        Subject s = SecurityUtils.getSubject();

        //2.判断当前用户是否被认证，并做相关处理
        if(!s.isAuthenticated()){
            //UsernamePasswordToken 令牌类  稍后会把保存在里面账号密码和shiro的身份和凭证比对
            UsernamePasswordToken upt = new UsernamePasswordToken(p.getUsername(),p.getPassword());
            upt.setRememberMe(true);
            try {
                System.out.println("jingqul   ddddd");
                //进行认证(因为我们写了自定义的realm类，所以会自动到realm类里面去认证)
                s.login(upt);
                //登录成功
                //保存用户名在session
                s.getSession().setAttribute("userName",p.getUsername());
                Set<String> roles = new HashSet<String>();
                //查询数据库得到用户的权限
                String role = pm.queryPersonByPersonName(p.getUsername()).getRole();
                roles.add(role);
                s.getSession().setAttribute("roles",roles);

                //判断复选框的状态
                if(remember.equals("YES")){
                    //1.创建cookie  Servlet  cookie的使用
                    Cookie c = new Cookie("USERNAME",p.getUsername());
                    Cookie c2 = new Cookie("PASSWORD",p.getPassword());
                    //2.设置cookie的时间
                    c.setMaxAge(30*24*60*60);
                    c2.setMaxAge(30*24*60*60);
                    //3.将cookie回写给浏览器
                    response.addCookie(c);
                    response.addCookie(c2);

                    //在shiro  cookie的使用
                    //1.创建SimpleCookie
                    SimpleCookie simpleCookie = new SimpleCookie();
                    //2,在cookie存值
                    simpleCookie.setName("USERNAME");
                    simpleCookie.setValue(p.getUsername());

                    simpleCookie.setName("PASSWORD");
                    simpleCookie.setValue(p.getPassword()+p.getUsername());
                    //3.设置cookie时候
                    simpleCookie.setMaxAge(30*24*60*60);
                    //4.回写给浏览器
                    simpleCookie.saveTo(request,response);
                }else {
                    Cookie[] cookies = request.getCookies();
                    //System.out.println(cookies);
                    if(cookies!=null){
                        for (Cookie c: cookies ) {
                            if(c.getName().equals("USERNAME")){
                                //System.out.println(222);
                                //servlet 如何删除Cookie ,将时间设置为0 并返回给浏览器
                                c.setMaxAge(0);
                                response.addCookie(c);
                                SimpleCookie sc = new SimpleCookie();
                                sc.setName("USERNAME");
                                sc.setValue("");
                                sc.setMaxAge(30);
                                sc.saveTo(request, response);
                            }
                            if(c.getName().equals("PASSWORD")){
                                SimpleCookie sc = new SimpleCookie();
                                sc.setName("PASSWORD");
                                sc.setValue("");
                                sc.setMaxAge(30);
                                sc.saveTo(request, response);
                            }
                        }
                    }
                }
                return "success";
            }catch (AuthenticationException e){
                //登录失败
                return "ERROR";
            }
        }
        return null;
    }

    //查询是否有所需要的cookie
    @Override
    public User queryCookie(HttpServletRequest request) {

        User p = new User();
        //得到所有的cookie
        // System.out.println(5555);
        Cookie[] cookies = request.getCookies();
        //System.out.println(cookies);
        if(cookies!=null){
            for (Cookie c: cookies ) {
                if(c.getName().equals("USERNAME")){
                    p.setUsername(c.getValue());
                }
                if(c.getName().equals("PASSWORD")){
                    p.setPassword(c.getValue().replace(p.getUsername(),""));
                }

            }
            return p;
        }

        p.setUsername("");
        return p;
    }

    //查询所有用户
    @Override
    public HashMap<String, Object> queryAllUser(User user) {
        //1.设置每页的查询页码，每页显示的行数
        PageHelper.startPage(user.getPage(),user.getRow());
        //2.查询自定义sql
        List<User> list = pm.selectByPage(user);
        //3.转换成分页对象
        PageInfo<User> pageInfo = new PageInfo<User>(list);

        //构建数据类型
        HashMap<String, Object> map = new HashMap<String, Object>();
        //结果集
        map.put("list",pageInfo.getList());
        //总条数
        map.put("count",pageInfo.getTotal());
        //获取上一页
        map.put("prePage",pageInfo.getPrePage());
        //获取下一页
        map.put("nextPage",pageInfo.getNextPage());
        //首页
        map.put("indexPage",pageInfo.getFirstPage());
        //末页
        map.put("endPage",pageInfo.getLastPage());

        map.put("allPage",pageInfo.getPageSize());

        return map;
    }

    @Override
    public User selectByUsername(String username) {
        return pm.selectByUsername(username);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return pm.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return pm.updateByPrimaryKey(user);
    }

    @Override
    public int delete(int userid) {
        return pm.deleteByPrimaryKey(userid);
    }


}
