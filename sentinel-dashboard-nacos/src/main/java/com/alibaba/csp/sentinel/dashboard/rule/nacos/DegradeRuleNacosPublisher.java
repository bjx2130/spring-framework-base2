package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nacos.api.config.ConfigService;

@Component("degradeRuleNacosPublisher")
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {

	@Autowired
	private ConfigService configService;

	public static final String FLOW_DATA_ID_POSTFIX = "-sentinel-degrade";
	public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
		List<DegradeRuleEntity> list = new ArrayList<DegradeRuleEntity>();
		for (DegradeRuleEntity rule : rules) {
			DegradeRuleEntity degradeRule = JSON.parseObject(JSON.toJSONString(rule), DegradeRuleEntity.class);
			list.add(degradeRule);
		}
		// 把对象转成格式化json字符串
		String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat);
		configService.publishConfig(app + FLOW_DATA_ID_POSTFIX, GROUP_ID, content);
    }
}
