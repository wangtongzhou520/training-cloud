package org.training.cloud.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * key value键值对
 *
 * @author wangtongzhou
 * @since 2023-05-10 21:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<K,V> {
    private K key;
    private V value;
}
