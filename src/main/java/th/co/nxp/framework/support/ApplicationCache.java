package th.co.nxp.framework.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import th.co.nxp.framework.preferences.constant.MessageConstants.MESSAGE_LANG;
import th.co.nxp.framework.preferences.persistence.repository.GeoAmphurRepository;
import th.co.nxp.framework.preferences.persistence.repository.GeoDistrictRepository;
import th.co.nxp.framework.preferences.persistence.repository.GeoProvinceRepository;
import th.co.nxp.framework.preferences.persistence.repository.GeoSectorRepository;
import th.co.nxp.framework.preferences.persistence.repository.MessageRepository;
import th.co.nxp.framework.preferences.persistence.repository.ParameterGroupRepository;
import th.co.nxp.framework.preferences.persistence.repository.ParameterInfoRepository;
import th.co.nxp.framework.preferences.vo.GeoAmphurVo;
import th.co.nxp.framework.preferences.vo.GeoDistrictVo;
import th.co.nxp.framework.preferences.vo.GeoProvinceVo;
import th.co.nxp.framework.preferences.vo.GeoSectorVo;
import th.co.nxp.framework.preferences.vo.MessageVo;
import th.co.nxp.framework.preferences.vo.ParameterGroupVo;
import th.co.nxp.framework.preferences.vo.ParameterInfoVo;
import th.co.nxp.framework.support.domain.GeoAmphur;
import th.co.nxp.framework.support.domain.GeoDistrict;
import th.co.nxp.framework.support.domain.GeoProvince;
import th.co.nxp.framework.support.domain.GeoSector;
import th.co.nxp.framework.support.domain.Message;
import th.co.nxp.framework.support.domain.ParamGroup;
import th.co.nxp.framework.support.domain.ParamInfo;

@Component
public class ApplicationCache {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationCache.class);

	// Parameter and Message
	private ParameterGroupRepository parameterGroupRepository;
	private ParameterInfoRepository parameterInfoRepository;
	private MessageRepository messageRepository;
	// Geography
	private GeoSectorRepository geoSectorRepository;
	private GeoProvinceRepository geoProvinceRepository;
	private GeoAmphurRepository geoAmphurRepository;
	private GeoDistrictRepository geoDistrictRepository;

	// Parameter and Message
	private static final ConcurrentHashMap<String, ParamGroupWrapper> PARAM_GROUP_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, Message> MESSAGE_MAP = new ConcurrentHashMap<>();
	// Geography
	private static final List<GeoSector> GEO_SECTOR_LIST = new ArrayList<>();
	private static final List<GeoProvince> GEO_PROVINCE_LIST = new ArrayList<>();
	private static final List<GeoAmphur> GEO_AMPHUR_LIST = new ArrayList<>();
	private static final List<GeoDistrict> GEO_DISTRICT_LIST = new ArrayList<>();
	private static final ConcurrentHashMap<String, List<GeoProvince>> GEO_PROVINCE_MAPPER = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, List<GeoAmphur>> GEO_AMPHUR_MAPPER = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, List<GeoDistrict>> GEO_DISTRICT_MAPPER = new ConcurrentHashMap<>();
	// Excise Master Data

	@Autowired
	public ApplicationCache(ParameterGroupRepository parameterGroupRepository,
			ParameterInfoRepository parameterInfoRepository, MessageRepository messageRepository,
			GeoSectorRepository geoSectorRepository, GeoProvinceRepository geoProvinceRepository,
			GeoAmphurRepository geoAmphurRepository, GeoDistrictRepository geoDistrictRepository) {
		this.parameterGroupRepository = parameterGroupRepository;
		this.parameterInfoRepository = parameterInfoRepository;
		this.messageRepository = messageRepository;
		this.geoSectorRepository = geoSectorRepository;
		this.geoProvinceRepository = geoProvinceRepository;
		this.geoAmphurRepository = geoAmphurRepository;
		this.geoDistrictRepository = geoDistrictRepository;
	}

	/** Reload */
	@PostConstruct
	public synchronized void reloadCache() {
		logger.info("ApplicationCache Reloading...");
//		loadParameter();
//		loadMessage();
//		loadGeography();
		logger.info("ApplicationCache Reloaded");
	}

	/********************* Method for Get Cache - Start *********************/
	/** Parameter Group & Parameter Info */
	public static List<ParamGroup> getParamGroupList() {
		List<ParamGroup> resultList = new ArrayList<>();
		for (Entry<String, ParamGroupWrapper> entry : PARAM_GROUP_MAP.entrySet()) {
			resultList.add(entry.getValue().getParamGroup());
		}
		return Collections.unmodifiableList(resultList);
	}

	public static ParamGroup getParamGroupByCode(String paramGroupCode) {
		return PARAM_GROUP_MAP.get(paramGroupCode).getParamGroup();
	}

	public static ParamInfo getParamInfoByCode(String paramGroupCode, String paramInfoCode) {
		return PARAM_GROUP_MAP.get(paramGroupCode).getParamInfoCodeMap().get(paramInfoCode);
	}

	public static List<ParamInfo> getParamInfoListByGroupCode(String paramGroupCode) {
		ParamGroupWrapper paramGroupWrapper = PARAM_GROUP_MAP.get(paramGroupCode);

		List<ParamInfo> resultList = new ArrayList<>();
		if (paramGroupWrapper != null) {
			for (Entry<String, ParamInfo> entry : paramGroupWrapper.getParamInfoCodeMap().entrySet()) {
				resultList.add(entry.getValue());
			}
		}

		return Collections.unmodifiableList(resultList);
	}

	final class ParamGroupWrapper {

		private ParamGroup paramGroup;
		private Map<String, ParamInfo> paramInfoCodeMap = new LinkedHashMap<>();

		public ParamGroupWrapper(ParamGroup paramGroup, List<? extends ParamInfo> paramInfoList) {
			this.paramGroup = paramGroup;
			for (ParamInfo paramInfo : paramInfoList) {
				paramInfoCodeMap.put(paramInfo.getParamCode(), paramInfo);
			}
		}

		public ParamGroup getParamGroup() {
			return paramGroup;
		}

		public Map<String, ParamInfo> getParamInfoCodeMap() {
			return paramInfoCodeMap;
		}

	}

	/** Message */
	public static Message getMessage(String messageCode) {
		return MESSAGE_MAP.get(messageCode);
	}

	public static String getMessage(String messageCode, String lang) {
		Message message = MESSAGE_MAP.get(messageCode);
		String msgDesc = null;
		if (MESSAGE_LANG.EN.equals(lang)) {
			msgDesc = message.getMessageEn();
		} else if (MESSAGE_LANG.TH.equals(lang)) {
			msgDesc = message.getMessageTh();
		}
		return msgDesc;
	}

	/** Geography */
	public static List<GeoSector> getGeoSectorList() {
		return Collections.unmodifiableList(ObjectUtils.defaultIfNull(GEO_SECTOR_LIST, new ArrayList<>()));
	}

	public static List<GeoProvince> getGeoProvinceList() {
		return Collections.unmodifiableList(ObjectUtils.defaultIfNull(GEO_PROVINCE_LIST, new ArrayList<>()));
	}

	public static List<GeoProvince> getGeoProvinceList(String sectorCode) {
		return Collections
				.unmodifiableList(ObjectUtils.defaultIfNull(GEO_PROVINCE_MAPPER.get(sectorCode), new ArrayList<>()));
	}

	public static List<GeoAmphur> getGeoAmphurList() {
		return Collections.unmodifiableList(ObjectUtils.defaultIfNull(GEO_AMPHUR_LIST, new ArrayList<>()));
	}

	public static List<GeoAmphur> getGeoAmphurList(String proviceCode) {
		return Collections
				.unmodifiableList(ObjectUtils.defaultIfNull(GEO_AMPHUR_MAPPER.get(proviceCode), new ArrayList<>()));
	}

	public static List<GeoDistrict> getGeoDistrictList() {
		return Collections.unmodifiableList(ObjectUtils.defaultIfNull(GEO_DISTRICT_LIST, new ArrayList<>()));
	}

	public static List<GeoDistrict> getGeoDistrictList(String amphurCode) {
		return Collections
				.unmodifiableList(ObjectUtils.defaultIfNull(GEO_DISTRICT_MAPPER.get(amphurCode), new ArrayList<>()));
	}

	/********************* Method for Get Cache - End *********************/

	private void loadParameter() {
		logger.info("load Paramter loading...");

		PARAM_GROUP_MAP.clear();

		List<th.co.nxp.framework.preferences.persistence.entity.ParameterGroup> paramGroupList = parameterGroupRepository
				.findAll();
		List<th.co.nxp.framework.preferences.persistence.entity.ParameterInfo> paramInfoList = parameterInfoRepository
				.findAll();
		List<ParamInfo> paramInfoVoList = null;
		ParameterGroupVo paramGroupVo = null;
		ParameterInfoVo paramInfoVo = null;

		for (th.co.nxp.framework.preferences.persistence.entity.ParameterGroup paramGroup : paramGroupList) {
			paramInfoVoList = new ArrayList<>();
			for (th.co.nxp.framework.preferences.persistence.entity.ParameterInfo paramInfo : paramInfoList) {
				if (paramGroup.getParamGroupCode().equals(paramInfo.getParamGroupCode())) {
					paramInfoVo = new ParameterInfoVo();
					paramInfoVo.setParamInfoId(paramInfo.getParamInfoId());
					paramInfoVo.setParamGroupCode(paramInfo.getParamGroupCode());
					paramInfoVo.setParamCode(paramInfo.getParamCode());
					paramInfoVo.setValue1(paramInfo.getValue1());
					paramInfoVo.setValue2(paramInfo.getValue2());
					paramInfoVo.setValue3(paramInfo.getValue3());
					paramInfoVo.setValue4(paramInfo.getValue4());
					paramInfoVo.setValue5(paramInfo.getValue5());
					paramInfoVo.setValue6(paramInfo.getValue6());
					paramInfoVo.setSortingOrder(paramInfo.getSortingOrder());
					paramInfoVo.setIsDefault(paramInfo.getIsDefault());
					paramInfoVoList.add(paramInfoVo);
				}
			}
			paramInfoVoList.sort((p1, p2) -> p1.getSortingOrder() - p2.getSortingOrder());
			PARAM_GROUP_MAP.put(paramGroup.getParamGroupCode(), new ParamGroupWrapper(paramGroupVo, paramInfoVoList));
		}

		logger.info("load Paramter loaded [{}]", PARAM_GROUP_MAP.size());
	}

	private void loadMessage() {
		logger.info("load Message loading...");

		MESSAGE_MAP.clear();

		List<th.co.nxp.framework.preferences.persistence.entity.Message> messageList = messageRepository.findAll();

		MessageVo messageVo = null;
		for (th.co.nxp.framework.preferences.persistence.entity.Message message : messageList) {
			messageVo = new MessageVo();
			messageVo.setMessageId(message.getMessageId());
			messageVo.setMessageCode(message.getMessageCode());
			messageVo.setMessageEn(message.getMessageEn());
			messageVo.setMessageTh(message.getMessageTh());
			messageVo.setMessageType(message.getMessageType());
			MESSAGE_MAP.put(messageVo.getMessageCode(), messageVo);
		}

		logger.info("load Message loaded [{}]", MESSAGE_MAP.size());
	}

	private void loadGeography() {
		logger.info("load Geography loading...");
		GEO_SECTOR_LIST.clear();
		GEO_PROVINCE_MAPPER.clear();
		GEO_AMPHUR_MAPPER.clear();
		GEO_DISTRICT_MAPPER.clear();

		/** Geography */
		List<th.co.nxp.framework.preferences.persistence.entity.GeoSector> geoSectorList = geoSectorRepository
				.findAll();
		GeoSectorVo sectorVo = null;
		for (th.co.nxp.framework.preferences.persistence.entity.GeoSector sector : geoSectorList) {
			sectorVo = new GeoSectorVo();
			sectorVo.setSectorCode(sector.getSectorCode());
			sectorVo.setSectorName(sector.getSectorName());
			GEO_SECTOR_LIST.add(sectorVo);
		}
		GEO_SECTOR_LIST.sort((p1, p2) -> p1.getSectorCode().compareTo(p2.getSectorCode()));

		/** Province */
		List<th.co.nxp.framework.preferences.persistence.entity.GeoProvince> geoProvinceList = geoProvinceRepository
				.findAll();
		List<GeoProvince> provinceList = null;
		GeoProvinceVo provinceVo = null;
		try {
			for (th.co.nxp.framework.preferences.persistence.entity.GeoProvince province : geoProvinceList) {
				provinceList = GEO_PROVINCE_MAPPER.get(province.getSectorCode());
				if (provinceList == null) {
					provinceList = new ArrayList<GeoProvince>();
				}
				provinceVo = new GeoProvinceVo();
				provinceVo.setSectorCode(province.getSectorCode());
				provinceVo.setProvinceCode(province.getProvinceCode());
				provinceVo.setProvinceName(province.getProvinceName());
				provinceList.add(provinceVo);
				GEO_PROVINCE_LIST.add(provinceVo);
				GEO_PROVINCE_MAPPER.put(provinceVo.getSectorCode(), provinceList);
			}
			GEO_PROVINCE_LIST.sort((p1, p2) -> p1.getProvinceCode().compareTo(p2.getProvinceCode()));
			GEO_PROVINCE_MAPPER.entrySet()
					.forEach(e -> e.getValue().sort((p1, p2) -> p1.getProvinceCode().compareTo(p2.getProvinceCode())));
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		/** Amphur */
		List<th.co.nxp.framework.preferences.persistence.entity.GeoAmphur> geoAmphurList = geoAmphurRepository
				.findAll();
		List<GeoAmphur> amphurList = null;
		GeoAmphurVo amphurVo = null;
		for (th.co.nxp.framework.preferences.persistence.entity.GeoAmphur amphur : geoAmphurList) {
			amphurList = GEO_AMPHUR_MAPPER.get(amphur.getAmphurCode().substring(0, 2));
			if (amphurList == null) {
				amphurList = new ArrayList<GeoAmphur>();
			}
			amphurVo = new GeoAmphurVo();
			amphurVo.setAmphurCode(amphur.getAmphurCode());
			amphurVo.setAmphurName(amphur.getAmphurName());
			amphurList.add(amphurVo);
			GEO_AMPHUR_LIST.add(amphurVo);
			GEO_AMPHUR_MAPPER.put(amphurVo.getAmphurCode().substring(0, 2), amphurList);
		}
		GEO_AMPHUR_LIST.sort((p1, p2) -> p1.getAmphurCode().compareTo(p2.getAmphurCode()));
		GEO_AMPHUR_MAPPER.entrySet()
				.forEach(e -> e.getValue().sort((p1, p2) -> p1.getAmphurCode().compareTo(p2.getAmphurCode())));

		/** District */
		List<th.co.nxp.framework.preferences.persistence.entity.GeoDistrict> geoDistricList = geoDistrictRepository
				.findAll();
		List<GeoDistrict> districtList = null;
		GeoDistrictVo districtVo = null;
		for (th.co.nxp.framework.preferences.persistence.entity.GeoDistrict district : geoDistricList) {
			districtList = GEO_DISTRICT_MAPPER.get(district.getDistrictCode().substring(0, 4));
			if (districtList == null) {
				districtList = new ArrayList<GeoDistrict>();
			}
			districtVo = new GeoDistrictVo();
			districtVo.setDistrictCode(district.getDistrictCode());
			districtVo.setDistrictName(district.getDistrictName());
			districtList.add(districtVo);
			GEO_DISTRICT_LIST.add(districtVo);
			GEO_DISTRICT_MAPPER.put(districtVo.getDistrictCode().substring(0, 4), districtList);
		}
		GEO_DISTRICT_LIST.sort((p1, p2) -> p1.getDistrictCode().compareTo(p2.getDistrictCode()));
		GEO_DISTRICT_MAPPER.entrySet()
				.forEach(e -> e.getValue().sort((p1, p2) -> p1.getDistrictCode().compareTo(p2.getDistrictCode())));

		logger.info("load Geography loaded Sector[{}], Province[{}], Amphur[{}], District[{}]", GEO_SECTOR_LIST.size(),
				GEO_PROVINCE_LIST.size(), GEO_AMPHUR_LIST.size(), GEO_DISTRICT_LIST.size());
	}

}
