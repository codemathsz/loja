package br.com.olimposistema.aipa.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.JPATransactionInterceptor;
import br.com.caelum.vraptor.view.Results;
import br.com.olimposistema.aipa.dao.IdInvalidoException;

@Intercepts(after = JPATransactionInterceptor.class)
@AcceptsWithAnnotations(Api.class)
public class ApiInterceptor {
	
	@Inject Result result;
	
	@AroundCall
	public void verificaSeTeveAlgumErroAoChamarAPI(SimpleInterceptorStack stack) {

		try {
			stack.next();
		}catch (IdInvalidoException e) {
			result.use(Results.http()).sendError(400, e.getMessage());
		}catch (ConstraintViolationException e) {
			result.use(Results.http()).sendError(400, exceptionRootCauseMessage(e));
		}catch (ValidationException e) {
			result.use(Results.http()).sendError(400, e.getMessage());
		}catch (Exception e) {
			String stackString = extractStackStringFor(e);
			List<String> exceptions = Arrays.asList(
					"ConstraintViolationException",
					"ValidationException"
			);
			
			for (String exceptionString : exceptions) {
				if(stackString.contains(exceptionString)) {
					result.use(Results.http()).sendError(400, exceptionRootCauseMessage(e));
					return;
				}
			}
			result.use(Results.http()).sendError(500, exceptionRootCauseMessage(e));
		}
	}

	private String extractStackStringFor(Exception e){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		try {
			String stackString = os.toString("UTF8");
			return stackString; 
		}catch (Exception ex) {}
		return "";
	}
	
	public static String exceptionRootCauseMessage(Exception e) {
		e.printStackTrace();
		Throwable t = e.getCause();
		String errorMessage = "";
		
	    if (t != null) {			
			while (t.getCause() != null) {
		        t = t.getCause();
		    }
			errorMessage = t.getLocalizedMessage();
	    } else {
	    	errorMessage = e.getLocalizedMessage();
	    }
	    
	    return errorMessage;
	}

}
