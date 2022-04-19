package br.com.olimposistema.aipa.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.JPATransactionInterceptor;
import br.com.caelum.vraptor.view.Results;

//@Intercepts(before=JPATransactionInterceptor.class)
//@RequestScoped
public class JPAExceptionInterceptor {

	@Inject Result result;
   
    @AroundCall
    public void intercept(SimpleInterceptorStack stack) throws Exception {
    	try {  
		    stack.next();  
	    } catch (Exception e) {
//	    	String message = exceptionRootCauseMessage(e);
//	    	throw new Exception(message);
	    	//result.use(Results.http()).sendError(500, message);
	    	//return;
	    }  
    }	 
    
    
}
