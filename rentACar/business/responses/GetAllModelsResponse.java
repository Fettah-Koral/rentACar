package kodlama.io.rentACar.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllModelsResponse {
	
	private int id;
	
	private String name;
	
	private String brandName; // varsayalım ki name döndürmek istiyoruz
	
	//private int brandId;
}
