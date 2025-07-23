package com.lyh.chronos.lyhtetmplateproject.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }
    public static <V> V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    /**
     * 将一个对象列表复制到另一个类型列表中。
     * 该方法通过流式处理源对象列表，将每个对象转换为指定的新类型，然后收集到一个新的列表中。
     * 这种方法适用于需要将一种类型的列表转换为另一种类型的场景，例如在数据绑定或数据转换过程中。
     *
     * @param list 源对象列表，这些对象将被复制并转换为新类型。
     * @param clazz 目标对象的类，用于实例化新对象。
     * @param <O> 源对象的类型参数。
     * @param <V> 目标对象的类型参数。
     * @return 包含转换后对象的新列表。
     */
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        // 使用流式处理来转换列表中的每个对象
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

}
