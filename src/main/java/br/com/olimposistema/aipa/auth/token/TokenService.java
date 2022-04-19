package br.com.olimposistema.aipa.auth.token;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import br.com.olimposistema.aipa.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService implements GeradorToken {

	static final String FRASE_SEGREDO = "@Frase#Para@Criptografia#Qual@Super@#@#";
	static final int DIAS_VALIDADE_TOKEN = 90;
	
	@Override
	public String gerarToken(String login){
		JwtBuilder construtor = geraTokenConstrutor();
		construtor.setIssuer(login);
		return construtor.compact();//Constrói o token retornando ele como uma String
	}
		
	@Override
	public String gerarToken(User userpayload) {
		
		Map<String, Object> payload = new HashMap<String, Object>();

		payload.put("id", userpayload.getId());
		payload.put("nome", userpayload.getNome());
		payload.put("email", userpayload.getEmail());
		//payload.put("celular", userpayload.getCelular());
		payload.put("role", userpayload.getRole());
		
		JwtBuilder construtor = geraTokenConstrutor();
		construtor.setIssuer(userpayload.getEmail());
		construtor.addClaims(payload);
		return construtor.compact();//Constrói o token retornando ele como uma String

	}
	
	
	@Override
	public boolean validaToken(String token) {
		
		try{
		   //JJWT vai validar o token caso o token não seja valido ele vai executar uma exeption
		   //o JJWT usa a frase segredo pra descodificar o token e ficando assim possivel
		   //recuperar as informações que colocamos no payload
		   Claims claims = Jwts.parser()     

			     .setSigningKey(DatatypeConverter.parseBase64Binary(FRASE_SEGREDO))

				.parseClaimsJws(token).getBody();

				 //Aqui é um exemplo que se o token for valido e descodificado 
				 //vai imprimir o login que foi colocamos no token
				 System.out.println(claims.getIssuer());
		   return true;

		}catch(Exception ex){
			return false;
		}
	}
	
	@Override
	public Date getDataValidadeTokenStartingNow() {
		return getPegaDataAtualESomaDias(DIAS_VALIDADE_TOKEN);
	}
	
	private JwtBuilder geraTokenConstrutor() {
		Integer expiraEmDias = DIAS_VALIDADE_TOKEN;
		
		//Defini qual vai ser o algoritmo da assinatura no caso vai ser o HMAC SHA512
		SignatureAlgorithm algoritimoAssinatura = SignatureAlgorithm.HS512;

		//Data atual que data que o token foi gerado
		Date agora = new Date();

		//Define até que data o token é pelo quantidade de dias que foi passo pelo parâmetro expiraEmDias
		Calendar expira = Calendar.getInstance();
		expira.add(Calendar.DAY_OF_MONTH, expiraEmDias);

		//Encoda a frase segredo pra base64 pra ser usada na geração do token 
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FRASE_SEGREDO);

		SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, algoritimoAssinatura.getJcaName());

		
		
	
		
		//E finalmente utiliza o JWT builder pra gerar o token
		
		JwtBuilder construtor = Jwts.builder()
			.setIssuedAt(agora)//Data que o token foi gerado
			.signWith(algoritimoAssinatura, key)//coloca o algoritmo de assinatura e frase segredo já encodada
			.setExpiration(expira.getTime());// coloca até que data que o token é valido
		return construtor;
	}

	private Date getPegaDataAtualESomaDias(int somardias) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +somardias);
		return calendar.getTime();
	}
}
