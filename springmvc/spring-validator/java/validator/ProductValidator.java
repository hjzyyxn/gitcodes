package validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Product product = (Product) target;
		ValidationUtils.rejectIfEmpty(errors, "name", "productname.required");
		ValidationUtils.rejectIfEmpty(errors, "price", "price.required");
		ValidationUtils.rejectIfEmpty(errors, "productionDate", "productiondate.required");
		BigDecimal price = product.getPrice();
		if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
			errors.rejectValue("price", "price.negative");
		}
		LocalDate productionDate = product.getProductionDate();
		if (productionDate != null) {
			if (productionDate.isAfter(LocalDate.now())) {
				errors.rejectValue("productionDate", "productiondate.invalid");
			}
		}

	}

}
