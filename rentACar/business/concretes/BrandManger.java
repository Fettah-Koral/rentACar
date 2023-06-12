package kodlama.io.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.business.rules.BrandBusinessRules;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.BrandRepository;
import kodlama.io.rentACar.entities.concretes.Brand;
import lombok.AllArgsConstructor;

@Service // bu sınıf bir business nesnesidir
@AllArgsConstructor
public class BrandManger implements BrandService {
	
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	private BrandBusinessRules brandBusinessRules;
	
	@Override
	public List<GetAllBrandsResponse> getAll() {

		List<Brand> brands = brandRepository.findAll();

		List<GetAllBrandsResponse> brandResponse = brands.stream()
			.map(brand->this.modelMapperService.forResponse()
					.map(brand, GetAllBrandsResponse.class)).collect(Collectors.toList());
		
		
		return brandResponse;
	}



	@Override
	public void add(CreateBrandRequest createBrandRequest) {
		
		this.brandBusinessRules.checkIfBrandNameExists(createBrandRequest.getName());
		// kurala uyulmazsa hata fırlatır ve işlem gerçeleştirilmez.
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		this.brandRepository.save(brand);
	}



	@Override
	public void update(UpdateBrandRequest updateBrandRequest) {
		Brand brand = modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandRepository.save(brand);
		
	}



	@Override
	public GetByIdBrandResponse getById(int id) {
		Brand brand = brandRepository.findById(id).orElseThrow();
		GetByIdBrandResponse brandResponse = modelMapperService.forResponse().map(brand, GetByIdBrandResponse.class);
		return brandResponse;
	}



	@Override
	public void delete(int id) {
		this.brandRepository.deleteById(id);
	}

}
