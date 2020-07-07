package vip.mate.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import vip.mate.code.entity.SysDataSource;
import vip.mate.code.mapper.SysDataSourceMapper;
import vip.mate.code.service.ISysDataSourceService;
import vip.mate.core.web.util.CollectionUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据源表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-07
 */
@Service
public class SysDataSourceServiceImpl extends ServiceImpl<SysDataSourceMapper, SysDataSource> implements ISysDataSourceService {

    @Override
    public IPage<SysDataSource> listPage(Map<String, String> query) {
        long current = CollectionUtil.strToLong(query.get("current"), 0L);
        long size = CollectionUtil.strToLong(query.get("size"), 0L);
        IPage<SysDataSource> page = new Page<>(current, size);
        String keyword = String.valueOf(query.get("keyword"));
        String startDate = String.valueOf(query.get("startDate"));
        String endDate = String.valueOf(query.get("endDate"));
        LambdaQueryWrapper<SysDataSource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(startDate) && !startDate.equals("null")) {
            lambdaQueryWrapper.between(SysDataSource::getCreateTime, startDate, endDate);
        }
        if (StringUtils.isNotBlank(keyword) && !keyword.equals("null")) {
            lambdaQueryWrapper.like(SysDataSource::getName, keyword);
            lambdaQueryWrapper.or();
            lambdaQueryWrapper.like(SysDataSource::getId, keyword);
        }
        return this.baseMapper.selectPage(page, lambdaQueryWrapper);
    }
}
