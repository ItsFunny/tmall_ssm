/**
*
* @author joker 
* @date 创建时间：2018年2月13日 上午9:59:52
* 
*/
package com.tmall.store.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
* 
* @author joker 
* @date 创建时间：2018年2月13日 上午9:59:52
*/
public class MyShiroRealm extends AuthorizingRealm
{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		String account=(String) token.getPrincipal();
		String password=(String) token.getCredentials();
		return null;
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection)
	{
		return null;
	}

	

}
