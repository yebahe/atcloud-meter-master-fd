package net.lishen.dto.stress;

import lombok.Data;

/**
 * @Author：li.shen
 * 映射relation字段到这个对象
 */
@Data
public class CSVDataFileDTO {
    //文件名
    private String name;
    //远程路径
    private String remoteFilePath;
    //本地路径
    private String localFilePath;
    /**
     * 类型，目前支持CSV
     */
    private String sourceType="csv";

    private String delimiter = ",";

    private Boolean ignoreFirstLine = false;

    private Boolean recycle = false;

    private String variableNames;




}
