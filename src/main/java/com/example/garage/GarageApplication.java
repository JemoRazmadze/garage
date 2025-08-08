package com.example.garage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@EnableCaching
public class GarageApplication {
	public static void main(String[] args) {
		SpringApplication.run(GarageApplication.class, args);
	}
//CRUD operation, da mere wamogeba pagination-it
	// car entity{id, model, feri, chenis dzala, fasi}
	//application propertys gamartba

	//pagination: model, feris mixedvit, pasis mixedvit; page 0, size 10;
	//car entity-shi daamate value createdAt = long;
	//car servishi unda casetos concretuli dro rodis sheiqmna object-i
	//pagination parametrebis mixedbit unda wamoigo, da createdAt mixedvit tu parametri ar gadaaecema ubrlod daaubrune yvelaferi createdat mixedvit


	//todo wamogeba "desc"-it: drois mixedvit da pasis

	//shevkmnat 20 obieqti(mankana) 5-5 ertnairi modeli magram gansxvavebuli perit da pasit


	//example:     Sort sortAsc = Sort.by(Sort.Direction.ASC, "name");

	// უველა ქრად მეთდზე უნდა გაწერო ვალიადია სადაც ნების მიერ უარყოფითი სემთხვევაში დაგიბრუნებს ერრორს(string - "error message")
}
