package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.ashokit.bindings.EligInfo;
import in.ashokit.service.EdService;


@RestController
@RequestMapping("/ed")
public class EdRestController {

	@Autowired
	private EdService edService;

	@GetMapping("/elig-determine/{caseNum}")
	public ResponseEntity<EligInfo> determineElig(@PathVariable Integer caseNum) {
		EligInfo eligData = edService.determineEligibility(caseNum);
		return new ResponseEntity<EligInfo>(eligData, HttpStatus.OK);
	}

//	@GetMapping("/co/{caseNum}")
//	public ResponseEntity<String> generateCo(@PathVariable Long caseNum) {
//		boolean status = eligService.generateCo(caseNum);
//		if (status) {
//			return new ResponseEntity<>("Notice Generated", HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>("Notice Not Generated", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
