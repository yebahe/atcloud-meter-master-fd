package net.lishen.service.stress.core;

import lombok.extern.slf4j.Slf4j;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.stress.CSVDataFileDTO;
import net.lishen.model.StressCaseDO;
import net.lishen.service.common.FileService;
import net.lishen.service.common.ResultSenderService;
import net.lishen.service.common.impl.KafkaResultSenderServiceImpl;
import net.lishen.util.CustomFileUtil;
import net.lishen.util.JsonUtil;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.SearchByClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.core
 * @Project：atcloud-meter
 * @name：StressJmxEngine
 * @Date：2024-01-22 11:41
 * @Filename：StressJmxEngine 模板方法的实现类
 */
@Slf4j
@Component
public class StressJmxEngine extends BaseStressEngine{

    @Autowired
    private  FileService fileService;

    public StressJmxEngine(){

    }
    public StressJmxEngine(StressCaseDO stressCaseDO, ReportDTO reportDTO, ApplicationContext applicationContext) {

        this.stressCaseDO = stressCaseDO;
        this.reportDTO = reportDTO;
        this.applicationContext = applicationContext;

    }

    @Override
    public void assembleTestPlan() {
        File jmxFile = null;
        //测试计划
        HashTree testPlanTree = new HashTree();
        try{
            jmxFile = File.createTempFile("jmeter-script",".jmx");

            try(FileWriter fileWriter = new FileWriter(jmxFile)){
                //读取远程文件写到本地jmxFile

                String url = fileService.getTempAccessFileUrl(stressCaseDO.getJmxUrl());
                String content = CustomFileUtil.readRemoteFile(url);
                fileWriter.write(content);
                //再这里不要考jdk的try(*=)帮你关闭，因为loadTree不会等你刷新，就开始load，需要提前flush和close
                fileWriter.flush();
                fileWriter.close();
                //加载测试计划树 jmx脚本
                testPlanTree = SaveService.loadTree(jmxFile);
                //转换测试计划树
                JMeter.convertSubTree(testPlanTree,false);
                ResultSenderService senderService = applicationContext.getBean(KafkaResultSenderServiceImpl.class);
                //获取自定义结果收集器
                EngineSampleCollector sampleCollector = super.getSampleCollector(senderService);
                //添加到测试计划
                testPlanTree.add(testPlanTree.getArray()[0],sampleCollector);
                //处理参数化相关逻辑
                parseParamFilesToScript(testPlanTree);
            }
        }catch (Exception e){
            log.error("组装测试计划失败：{}",e.getMessage());
            throw new RuntimeException("组装测试计划失败");
        }finally {
            //删除临时文件
            if(jmxFile != null){
                boolean flag = jmxFile.delete();
                if(!flag){
                    log.error("删除临时文件失败：{}",jmxFile.getAbsolutePath());
                }
            }
        }

        super.setTestPlanHashTree(testPlanTree);




    }

    /**
     * 处理参数化相关逻辑
     *
     * 获取远程参数文件
     * 解析relation字段映射到DTO
     * 对jmx脚本进行遍历，提取csvdataset对象到容器
     * 匹配，修改fileName属性为scv路径
     * @param testPlanTree
     */
    public void parseParamFilesToScript(HashTree testPlanTree) {

        //这个是一个json数组，每个元素对应可变参数csv文件
        String relation = stressCaseDO.getRelation();

        List<CSVDataFileDTO> csvDataFileDTOS = JsonUtil.json2List(relation, CSVDataFileDTO.class);

        //定义一个list存放jmx里所有的csv文件 CSVDATASET
        List<CSVDataSet> csvDataSetList = new ArrayList<>();

        //对jmx脚本进行遍历
        SearchByClass<TestElement> testElementVisitor = new SearchByClass<>(TestElement.class);
        testPlanTree.traverse(testElementVisitor);

        Collection<TestElement> searchResults = testElementVisitor.getSearchResults();

        //提取jmx脚本里的csv的配置类存储到list
        for(TestElement testElement : searchResults){
            if(testElement instanceof CSVDataSet csvDataSet) {
                boolean enabled = csvDataSet.getProperty("TestElement.enabled").getBooleanValue();
                if (enabled) {
                    csvDataSetList.add((CSVDataSet) testElement);
                }
            }
        }
        //遍历jmx中的csv文件，根据fileName进行关联
        for (CSVDataSet csvDataSet : csvDataSetList) {
            String csvDataSetSlotName = csvDataSet.getProperty("TestElement.name").getStringValue();
            if(csvDataFileDTOS!=null){
                for(CSVDataFileDTO csvDataFileDTO : csvDataFileDTOS){
                    if(csvDataFileDTO.getName().equals(csvDataSetSlotName)){
                        String localTempFile = fileService.copyRemoteFileToLocalTempFile(csvDataFileDTO.getRemoteFilePath());
                        if(localTempFile!=null){
                            csvDataSet.setProperty("filename",localTempFile);
                        }else {
                            throw new RuntimeException("csv拷贝到本地失败,jmx赋值本地csv路径失败");
                        }
                    }
                }
            }
        }


    }
}
