package com.justyoga.place.config;

import com.justyoga.place.domain.dao.DaoPackage;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = DaoPackage.class)
public class DataConfig {}
