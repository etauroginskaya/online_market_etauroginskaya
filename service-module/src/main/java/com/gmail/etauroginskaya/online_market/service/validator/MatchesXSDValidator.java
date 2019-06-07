package com.gmail.etauroginskaya.online_market.service.validator;

import com.gmail.etauroginskaya.online_market.service.validator.annotations.MatchesXSD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.time.LocalDateTime;

public class MatchesXSDValidator implements ConstraintValidator<MatchesXSD, MultipartFile> {

    private static final Logger logger = LoggerFactory.getLogger(MatchesXSDValidator.class);

    @Value("classpath:" + "${xsd.schema.path}")
    private Resource resourceFile;

    @Override
    public void initialize(MatchesXSD constraint) {
    }

    public boolean isValid(MultipartFile xml, ConstraintValidatorContext cxt) {
        if (xml == null) {
            logger.error("{} {} : xml file is null.", LocalDateTime.now(), getClass().getName());
            return false;
        }
        if (!resourceFile.exists()) {
            logger.error("{} {} : xsd schema not found.", LocalDateTime.now(), getClass().getName());
            return false;
        }
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new File(resourceFile.getURI()))
                    .newValidator()
                    .validate(new StreamSource(xml.getInputStream()));
        } catch (Exception e) {
            logger.info("{} {} : {} does not match the pattern.",
                    LocalDateTime.now(), getClass().getName(), xml.getOriginalFilename());
            return false;
        }
        return true;
    }
}