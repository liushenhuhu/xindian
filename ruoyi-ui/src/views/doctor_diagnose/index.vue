<template>
  <div class="app-container">

    <el-tabs type="border-card" @tab-click="handleClick" v-model="activeName" >
      <el-tab-pane label="待诊断数据" name="first">



        <el-form :model="queryParamsWeek" ref="queryFormWeek" size="small" :inline="true" label-width="68px">
          <el-form-item>
            <el-button icon="el-icon-refresh" size="mini" @click="getList">刷新</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="reportList">
<!--          <el-table-column type="selection" width="55" align="center" />-->
<!--          <el-table-column label="报告id" align="center" prop="reportId" />-->
          <el-table-column label="报告id" align="center" prop="pId" />
          <el-table-column label="诊断状态" align="center" prop="diagnosisStatus">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
            </template>
          </el-table-column>
<!--          <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" show-overflow-tooltip/>-->
          <el-table-column label="诊断医生" align="center" prop="diagnosisDoctor" />
          <el-table-column label="报告时间" align="center" prop="reportTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
<!--          <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" show-overflow-tooltip/>-->
          <el-table-column label="报告种类" align="center" prop="reportType">
            <template slot-scope="scope">
              <el-tag  v-if="scope.row.reportType=='week'">
                  周报
              </el-tag>
              <el-tag  v-else-if="scope.row.reportType.indexOf('JECGsingle') != -1">
                静态单导
              </el-tag>
              <el-tag  v-else-if="scope.row.reportType.indexOf('JECG4') != -1">
                静态4导
              </el-tag>
              <el-tag  v-else-if="scope.row.reportType.indexOf('JECG12') != -1">
                静态12导
              </el-tag>
              <el-tag  v-else>
                暂无类型
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDiagnose(scope.row)"
                v-hasPermi="['report:report:edit']"
              >诊断</el-button>
            </template>
          </el-table-column>
        </el-table>


      </el-tab-pane>
      <el-tab-pane label="已诊断数据" name="second">
        <el-tabs type="border-card" @tab-click="handleClickTwo" v-model="activeNameTwo" >
          <el-tab-pane label="心电" name="first">
            <div class="searchForm">
              <div class="title">信息查询</div>
              <div>
                <el-form :model="queryParamsEcg" ref="queryForm" size="small" :inline="true" label-width="100px" class="elformbox">
                  <div class="form-left" :class="{'form-left-hide':!showSearch}">
                    <el-form-item label="用户姓名" prop="patientName">
                      <el-input
                        v-model="queryParamsEcg.patientName"
                        placeholder="请输入患者姓名"
                        clearable
                        @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                    <!--<el-form-item label="名称模糊查询" prop="isSelect">-->
                    <!--  <el-radio-group v-model="queryParams.isSelect">-->
                    <!--    <el-radio label="1">开启</el-radio>-->
                    <!--    <el-radio label="2">禁用</el-radio>-->
                    <!--  </el-radio-group>-->
                    <!--</el-form-item>-->
                    <el-form-item label="用户性别" prop="patientSex">
                      <el-select placeholder="请选择性别" v-model="queryParamsEcg.patientSex">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="用户管理id" prop="pId">
                      <el-input
                        v-model="queryParamsEcg.pId"
                        placeholder="请输入用户管理id"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="用户电话" prop="patientPhone">
                      <el-input
                        v-model="queryParamsEcg.PatPhone"
                        placeholder="请输入用户电话"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="医院名称" prop="hospitalName">
                      <el-select v-model="queryParamsEcg.hospitalCode" placeholder="请选择医院代号">
                        <el-option
                          v-for="item in options"
                          :key="item.hospitalId"
                          :label="item.hospitalName"
                          :value="item.hospitalCode">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="设备号" prop="equipmentCode">
                      <el-input
                        v-model="queryParamsEcg.equipmentCode"
                        placeholder="请输入设备号"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="连接时间">
                      <el-date-picker
                        v-model="daterangeConnectionTime"
                        style="width: 205px"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                      ></el-date-picker>
                    </el-form-item>
                    <el-form-item label="在线状态" prop="onlineStatus">
                      <el-select v-model="queryParamsEcg.onlineStatus" placeholder="请选择在线状态" clearable>
                        <el-option
                          v-for="dict in dict.type.monitoring_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                        />
                      </el-select>
                    </el-form-item>
                    <el-form-item label="智能诊断" prop="intelligentDiagnosis">
                      <el-input
                        v-model="queryParamsEcg.intelligentDiagnosis"
                        placeholder="请输入智能诊断关键词"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="医生诊断" prop="diagnosisConclusion">
                      <el-input
                        v-model="queryParamsEcg.diagnosisConclusion"
                        placeholder="请输入医生诊断关键词"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="诊断医生" prop="diagnosisDoctor">
                      <el-input
                        v-model="queryParamsEcg.diagnosisDoctor"
                        placeholder="请输入诊断医生"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="风险等级" prop="ecgLevel">
                      <el-select v-model="queryParamsEcg.ecgLevel" placeholder="请选择风险等级" clearable>
                        <el-option
                          v-for="dict in dict.type.ecg_level"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                  <div class="form-right">
                    <el-form-item>
                      <el-button type="text" size="small" v-show="!showSearch" @click="unfoldSearchBox">展开<i
                        class="el-icon-arrow-down"/></el-button>
                      <el-button type="text" size="small" v-show="showSearch" @click="unfoldSearchBox">收起<i
                        class="el-icon-arrow-up"/></el-button>
                      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
                      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                    </el-form-item>
                  </div>
                </el-form>
              </div>
            </div>
            <div class="tablebox">
              <div class="table-hand">
                <div class="table-hand-left">
                  <el-select v-model="queryParamsEcg.ecgType" placeholder="请选择采集类型" clearable @change="getListEcg"
                             class="table-hand-left-select table-hand-left-select-type">
                    <el-option key="JECG" label="全部" value="JECG"></el-option>
                    <el-option
                      v-for="item in ecgList"
                      :key="item.label"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </div>
                <div class="hand-right">
                  <el-button type="text" size="small" icon="el-icon-refresh" @click="refreshList">刷新</el-button>
                  <el-button
                    type="success"
                    plain
                    icon="el-icon-view"
                    size="mini"
                    v-hasPermi="['ecg:show:conceal']"
                    @click="isShowNameClick">{{isShowName.name}}</el-button>
                </div>
              </div>
              <div class="table-content">
                <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange"
                          highlight-current-row @current-change="handleCurrentChange"  class="table-content-table">

                  <el-table-column type="selection" width="55" align="center"/>
                  <el-table-column label="上传时间" align="center" prop="connectionTime" width="100">
                    <template slot-scope="scope">
                      <span>{{ parseTime(scope.row.connectionTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                    </template>
                  </el-table-column>

                  <!--      <el-table-column label="用户管理id" align="center" prop="pId" show-overflow-tooltip/>
                        <el-table-column label="用户姓名" align="center" prop="patientName"/>
                        <el-table-column label="用户身份证号" align="center" prop="patientCode"/>
                        <el-table-column label="用户年龄" align="center" prop="patientAge"/>
                        <el-table-column label="用户性别" align="center" prop="patientSex">
                          <template slot-scope="scope">
                            <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>
                          </template>
                        </el-table-column>
                        <el-table-column label="用户来源" align="center" prop="patientSource"/>
                        <el-table-column label="用户电话" align="center" prop="patientPhone"/>
                        <el-table-column label="家属电话" align="center" prop="familyPhone"/>
                        <el-table-column label="监测状态" align="center" prop="monitoringStatus">
                          <template slot-scope="scope">
                            <dict-tag :options="dict.type.monitoring_status" :value="scope.row.monitoringStatus"/>
                          </template>
                        </el-table-column>
                        <el-table-column label="医院代号" align="center" prop="hospitalCode"/>
                        <el-table-column label="医院名称" align="center" prop="hospitalName" width="150"/>
                        <el-table-column label="设备号" align="center" prop="equipmentCode"/>
                        <el-table-column label="在线状态" align="center" prop="onlineStatus">
                          <template slot-scope="scope">
                            <dict-tag :options="dict.type.monitoring_status" :value="scope.row.onlineStatus"/>
                          </template>
                        </el-table-column>
                        <el-table-column label="心电种类" align="center" prop="ecgType">
                          <template slot-scope="scope">
                            <dict-tag :options="dict.type.ecg_type" :value="scope.row.ecgType"/>
                          </template>
                        </el-table-column>-->
                  <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" width="200"
                                   show-overflow-tooltip/>


                  <el-table-column label="用户姓名" align="center" prop="patientName" min-width="100">
                    <template slot-scope="scope">
                      <span v-if="isShowName.status===true">{{ scope.row.patientName }}</span>
                      <span v-else>{{hideMiddleName(scope.row.patientName)}}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="年龄" align="center" prop="patientAge" min-width="50">
                    <!-- <template slot-scope="scope">
                      <span v-if="isShowName.status===true">{{ scope.row.patientAge }}</span>
                      <span v-else>**</span>
                    </template> -->
                  </el-table-column>
                  <el-table-column label="性别" align="center" prop="patientSex" min-width="50"></el-table-column>

                  <!--      <el-table-column label="用户症状" align="center" prop="patientSymptom" show-overflow-tooltip/>-->
                  <!--      <el-table-column label="医院名称" align="center" prop="hospitalName"/>-->
                  <!--      <el-table-column label="报告时间" align="center" prop="reportTime" width="100" >-->
                  <!--        <template slot-scope="scope">-->
                  <!--          <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>-->
                  <!--        </template>-->
                  <!--      </el-table-column>-->
                  <el-table-column label="风险等级" align="center" prop="ecgLevel" >
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.ecg_level" :value="scope.row.ecgLevel"/>
                    </template>
                  </el-table-column>

                  <!--            <el-table-column label="是否标注预警状态" width="150" align="center" prop="ecgIsLabel">-->
                  <!--              <template slot-scope="scope">-->
                  <!--                <dict-tag :options="dict.type.if_status" :value="scope.row.ecgIsLabel"/>-->
                  <!--              </template>-->
                  <!--            </el-table-column>-->
                  <!--
                  <el-table-column label="是否标注预警状态" align="center" prop="ecgIsLabel">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.if_status" :value="scope.row.ecgIsLabel"/>
                    </template>
                  </el-table-column>-->


                  <el-table-column label="心率(bpm)" align="center" prop="ecgAnalysisData" width="100">
                    <template slot-scope="scope">
                      <el-tag v-if="getAgeTypeSection(scope.row.patientAge,getEcgType(scope.row.ecgAnalysisData,'平均心率'))" type="danger">{{getEcgType(scope.row.ecgAnalysisData,"平均心率")}}</el-tag>
                      <el-tag v-if="!getAgeTypeSection(scope.row.patientAge,getEcgType(scope.row.ecgAnalysisData,'平均心率'))" >{{getEcgType(scope.row.ecgAnalysisData,"平均心率")}}</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="心肌梗塞" align="center" prop="ecgAnalysisData" width="100">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.ecgType.indexOf('JECG4')==-1">--</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))<70">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))<90" type="warning">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))<=100" type="danger">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_xingeng'))+'%'}}</el-tag>
                      <el-tag v-else>--</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="肥厚型心肌病" align="center" prop="ecgAnalysisData" width="100">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.ecgType.indexOf('JECG4')==-1">--</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))<70">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))<90" type="warning">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))<=100" type="danger">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_FHXXJB'))+'%'}}</el-tag>
                      <el-tag v-else>--</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="扩张型心肌病" align="center" prop="ecgAnalysisData" width="100">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.ecgType.indexOf('JECG4')==-1">--</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))<70">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))<90" type="warning">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))<=100" type="danger">{{getBai(getEcgType(scope.row.ecgAnalysisData,'p_KZXXJB'))+'%'}}</el-tag>
                      <el-tag v-else>--</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="高血钾" align="center" prop="ecgAnalysisData" width="100">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.ecgType.indexOf('JECG4')==-1">--</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))<70">{{getBai(getEcgType(scope.row.ecgAnalysisData,"p_GaoJiaXie"))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))<90" type="warning">{{getBai(getEcgType(scope.row.ecgAnalysisData,"p_GaoJiaXie"))+'%'}}</el-tag>
                      <el-tag v-else-if="getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))!='--'&&getBai(getEcgType(scope.row.ecgAnalysisData,'p_GaoJiaXie'))<=100" type="danger">{{getBai(getEcgType(scope.row.ecgAnalysisData,"p_GaoJiaXie"))+'%'}}</el-tag>
                      <el-tag v-else>--</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="用户管理ID" align="center" prop="pId" width="200"></el-table-column>
                  <el-table-column label="医院" align="center" prop="hospitalName" min-width="200">
                    <template slot-scope="scope">
                      <span v-if="isShowName.status===true">{{ scope.row.hospitalName }}</span>
                      <span v-else>***************</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="心电种类" align="center" prop="ecgType" width="140">
                    <template slot-scope="scope">
                      <el-tag>
                        {{ scope.row.ecgType }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="诊断状态" align="center" prop="diagnosisStatus" width="100">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="诊断医生" align="center" width="100" prop="diagnosisDoctor">
                  </el-table-column>
                  <el-table-column label="医生诊断" align="center"  prop="diagnosisConclusion" width="200"
                                   show-overflow-tooltip/>
                  <el-table-column label="设备号" align="center" prop="equipmentCode" min-width="150"></el-table-column>
                  <el-table-column label="用户电话" align="center" prop="patientPhone" min-width="150">
                    <template slot-scope="scope">
                      <span v-if="isShowName.status===true">{{ scope.row.patientPhone }}</span>
                      <span v-else>***************</span>
                    </template>
                  </el-table-column>
                  <!--            <el-table-column label="家属电话" align="center" prop="familyPhone" min-width="150">-->
                  <!--              <template slot-scope="scope">-->
                  <!--                <span v-if="isShowName.status===true">{{ scope.row.familyPhone }}</span>-->
                  <!--                <span v-else>***************</span>-->
                  <!--              </template>-->
                  <!--            </el-table-column>-->
                  <el-table-column label="医生电话" align="center" prop="doctorPhone" min-width="150"></el-table-column>
                  <!--            <el-table-column label="用户身份证号" align="center" prop="patientCode" min-width="200">-->
                  <!--              <template slot-scope="scope">-->
                  <!--                <span v-if="isShowName.status===true">{{ scope.row.patientCode }}</span>-->
                  <!--                <span v-else>***************</span>-->
                  <!--              </template>-->
                  <!--            </el-table-column>-->
                  <el-table-column label="用户过往病史" align="center" prop="patientCode" min-width="200">
                    <template slot-scope="scope">
              <span v-if="isShowName.status===true">
                {{getMH(dict.type.medical_history, scope.row.pastMedicalHistory)}}
              </span>
                      <span v-else>***************</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width" fixed="right">
                    <template slot-scope="scope">
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-s-order"
                        @click="lookECG(scope.row)"
                        v-hasPermi="['jecg:report:look']"
                      >查看报告
                      </el-button>
                      <el-popover placement="left" width="50" trigger="click" style="margin-left:8px">
                        <el-button
                          style="margin-left: 10px;"
                          size="mini"
                          type="text"
                          icon="el-icon-s-operation"
                          @click="selectECG(scope.row)"
                          v-hasPermi="['jecg:report:diagnose']"
                          v-if="scope.row.diagnosisStatus!=1"
                        >选择医生诊断
                        </el-button>
                        <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-star-off"
                          v-hasPermi="['jecg:report:find']"
                          @click="findJEcgReport(scope.row)"
                        >查看静态报告
                        </el-button>
                        <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-eleme"
                          @click="lookHistoryData30(scope.row)"
                        >30天趋势图
                        </el-button>
                        <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-position"
                          @click="sendMsg(scope.row)"
                        >发送短信
                        </el-button>
                        <!--          <el-button-->
                        <!--            size="mini"-->
                        <!--            type="text"-->
                        <!--            icon="el-icon-edit"-->
                        <!--            @click="handleUpdate(scope.row)"-->
                        <!--            v-hasPermi="['patient_management:patient_management:export']"-->
                        <!--          >修改-->
                        <!--          </el-button>-->

                        <el-button
                          slot="reference"
                          size="small"
                          type="text">
                          更多<i class="el-icon-arrow-down"/>
                        </el-button>
                      </el-popover>

                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <pagination
                :total="total"
                :page.sync="queryParamsEcg.pageNum"
                :limit.sync="queryParamsEcg.pageSize"
                @pagination="getListEcg"
              />
            </div>
          </el-tab-pane>
          <el-tab-pane label="周报" name="second">
            <el-form :model="queryParamsWeek" ref="queryFormWeek" size="small" :inline="true" label-width="68px">
              <el-form-item label="患者电话" prop="patientPhone">
                <el-input
                  v-model="queryParamsWeek.patientPhone"
                  placeholder="请输入患者电话"
                  clearable
                  @keyup.enter.native="handleQueryWeek"
                />
              </el-form-item>
              <el-form-item label="医生电话" prop="doctorPhone">
                <el-input
                  v-model="queryParamsWeek.doctorPhone"
                  placeholder="请输入医生电话"
                  clearable
                  @keyup.enter.native="handleQueryWeek"
                />
              </el-form-item>
              <el-form-item label="医生名称" prop="doctorName">
                <el-input
                  v-model="queryParamsWeek.doctorName"
                  placeholder="请输入医生电话"
                  clearable
                  @keyup.enter.native="handleQueryWeek"
                />
              </el-form-item>
              <el-form-item label="患者名称" prop="patientName">
                <el-input
                  v-model="queryParamsWeek.patientName"
                  placeholder="请输入医生电话"
                  clearable
                  @keyup.enter.native="handleQueryWeek"
                />
              </el-form-item>
              <el-form-item label="诊断结论" prop="diagnosisConclusion">
                <el-input
                  v-model="queryParamsWeek.diagnosisConclusion"
                  placeholder="请输入诊断结论"
                  clearable
                  @keyup.enter.native="handleQueryWeek"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryWeek">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQueryWeek">重置</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="weekReportList" >
              <el-table-column label="报告id" align="center" prop="weekid" />
              <el-table-column label="患者电话" align="center" prop="patientPhone" />
              <el-table-column label="患者名称" align="center" prop="patientName" />
              <el-table-column label="医生电话" align="center" prop="doctorPhone" />
              <el-table-column label="医生名称" align="center" prop="doctorName" />
              <el-table-column label="诊断状态" align="center" prop="diagnosisStatus" >
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.diagnosisStatus==0" type="danger">未诊断</el-tag>
                  <el-tag v-if="scope.row.diagnosisStatus==1" type="success">已诊断</el-tag>
                  <el-tag v-if="scope.row.diagnosisStatus==2" type="warning">诊断中</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" />
              <el-table-column label="诊断时间" align="center" prop="updateTime" >
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleweekReportUpdate(scope.row)"
                  >查看报告</el-button>

                </template>
              </el-table-column>
            </el-table>

            <pagination
              v-show="totalWeek>0"
              :total="totalWeek"
              :page.sync="queryParamsWeek.pageNum"
              :limit.sync="queryParamsWeek.pageSize"
              @pagination="getListWeek"
            />
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane label="诊断统计" name="three">
        <DocDiagnose ref = "DocDiagnose" />
      </el-tab-pane>
    </el-tabs>


    <el-dialog title="选择医生诊断" :visible.sync="dialogFormVisible" width="300px">
      <el-cascader :options="option" clearable v-model="value"></el-cascader>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogForm">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="密码验证" :visible.sync="dialogFormVisibleVerifyAuthority">
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input placeholder="请输入密码" v-model="verifyForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleVerify">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<script>


import {getWebList, getWeekReportList, listReport} from "@/api/report/report";
import {docUpdate, getEcgType, listDoc, listPatient_management} from "@/api/patient_management/patient_management";
import {listHospitalId} from "@/api/hospital/hospital";
import {docList} from "@/api/doctor/doctor";
import {updateOnlineAll} from "@/api/online/online";
import DocDiagnose  from "@/views/statistics/statistics";

export default {
  name: 'DoctorDiagnosis',
  dicts: ['if', 'sex', 'monitoring_status', 'ecg_type', 'diagnosis_status', 'ecg_level', 'hospital_name_list', 'if_status',"medical_history",'doctor_diagnosis_status'],
  components: {
    DocDiagnose
  },
  data() {
    return {

      reportList:[],
      weekReportList:[],
      reportLists: {
        pId: null,
        dPhone: null,
        hospital: null,
      },
      activeName: sessionStorage.getItem('tag_doctor_diagnose')?sessionStorage.getItem('tag_doctor_diagnose') : 'first',
      activeNameTwo: 'first',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        diagnosisStatus: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        intelligentDiagnosis: null,
        pphone:null,
      },

      currentRow:null,
      currentScrollPos: 0,
      isRouterAlive: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      isShowName: {
        status: false,
        name: "显示信息"
      },
      option: [],
      value: [],
      option2: [],
      dialogFormVisible: false,
      dialogFormVisibleVerifyAuthority: false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      totalWeek: 0,
      verifyForm: {
        password: null,
        status: false
      },
      // 用户管理表格数据
      patient_managementList: [],
      // 弹出层标题
      title: "",
      ecgList: [],
      // 是否显示弹出层
      open: false,
      // 时间范围
      daterangeConnectionTime: [],
      options: [],
      ecgType: 'JECG',
      // 查询参数
      // 表单参数
      form: {},
      // 查询参数
      queryParamsEcg: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: 'JECG',
        PatPhone: null,
        diagnosisStatus: '1',
        intelligentDiagnosis: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex: null,
        isSelect: '2'
      },
      queryParamsWeek: {
        pageNum: 1,
        pageSize: 10,
        weekid: null,
        patientPhone: null,
        doctorPhone: null,
        patientName: null,
        doctorName: null,
        diagnosisStatus: 1,
        diagnosisConclusion: null
      },
      // 表单校验
      rules: {
        patientPhone: [
          {required: true, message: "用户电话不能为空", trigger: "blur"}
        ],
        hospitalCode: [
          {required: true, message: "医院代号不能为空", trigger: "blur"}
        ],
        equipmentCode: [
          {required: true, message: "设备号不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
      },

    }
  },
  beforeCreate() {
    // updateOnlineAll();
    console.log('beforeCreate')
  },
  activated() {
    console.log('activated')
    document.documentElement.scrollTop = this.currentScrollPos || 0
  },


  mounted() {
    window.addEventListener('beforeunload', this.handleBeforeUnload);
  },
  beforeDestroy() {
    window.removeEventListener('beforeunload', this.handleBeforeUnload);
  },

  created() {
    console.log('created')
    this.queryParams.diagnosisStatus = '2';
    this.handleClick()
  },
  methods: {
    handleBeforeUnload(event) {
      console.log("页面被关闭")
    },
    hideMiddleName(patientName) {
      if (patientName.length <= 1) {
        return "*"; // 一个字的则用一个 * 代替
      } else if (patientName.length === 2) {
        return patientName.charAt(0) + "*"; // 两个字的保留第一个字，后面用 * 代替
      } else {
        // let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 2) + patientName.charAt(patientName.length - 1);
        let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 1);
        return visibleChars; // 大于两个字的保留第一个字和最后一个字，中间用 * 代替
      }
    },
    //表格选中事件
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    unfoldSearchBox() {
      this.showSearch = !this.showSearch;
      this.$nextTick(() => {
        // this.updateTableHeight()
        this.$forceUpdate()
      })
    },
    tableRowClassName({row, rowIndex}) {
      /*console.log(row.ecgLevel)
      if (row.ecgLevel === 2) {
        return 'warning-row';
      } else if (row.ecgLevel === 3) {
        return 'success-row';
      }
      return '';*/
    },
    reload() {
      this.isRouterAlive = false;            //先关闭，
      this.$nextTick(function () {
        this.isRouterAlive = true;         //再打开
      })
    },
    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },
    selectECG(row) {
      this.reportLists.pId = row.pId
      this.dialogFormVisible = true;
      listDoc().then(r => {
        console.log(r)
        this.option = r.data
      })
    },
    dialogForm() {
      this.reportLists.dPhone = this.value[1]
      this.reportLists.hospital = this.value[0]
      console.log(this.reportLists)
      docUpdate(this.reportLists).then(r => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
        this.dialogFormVisible = false;
      })
    },

    handleClick(tab, event) {
      sessionStorage.setItem('tag_doctor_diagnose',this.activeName)
      if (this.activeName=='first'){
        this.queryParams.diagnosisStatus = '2';
        this.getList()
      }else if (this.activeName=='second'){
        getEcgType(this.ecgType).then(r => {
          this.ecgList = r.data
        })
        listHospitalId(null).then(r => {
          this.options = r.rows
        })
        docList().then(q => {
          this.option2 = q.data
        })
        this.getListEcg()
      }else{
        this.$refs.DocDiagnose.getList()
      }
    },
    /** 查询用户管理列表 */
    getListEcg() {
      this.loading = true;
      this.queryParamsEcg.params = {};
      if (null != this.daterangeConnectionTime && '' != this.daterangeConnectionTime) {
        this.queryParamsEcg.params["beginConnectionTime"] = this.daterangeConnectionTime[0];
        this.queryParamsEcg.params["endConnectionTime"] = this.daterangeConnectionTime[1];
      }
      if (this.queryParamsEcg.ecgType == null) {
        this.queryParamsEcg.ecgType = this.ecgType
      }
      listPatient_management(this.queryParamsEcg).then(response => {
        this.patient_managementList = response.rows;
        // 原先提交过的预警类型response.rows[].logDataType
        this.total = response.total;
        this.loading = false;
        if (this.queryParamsEcg.ecgType === 'JECGsingle') {
          this.queryParamsEcg.ecgType = null
        }
      });
    },
    /** 查询用户管理列表 */
    getListWeek() {
      this.loading = true;
      getWeekReportList(this.queryParamsWeek).then(response => {
        this.weekReportList = response.rows;
        this.totalWeek = response.total;
        this.loading = false;
      });
    },
    handleClickTwo(tab, event){
      if (this.activeNameTwo=='first'){
        this.queryParamsEcg.diagnosisStatus = '1';
        this.getListEcg()
      }else {
        this.getListWeek()
      }
    },

    /** 查询报告列表 */
    getList() {
      this.loading = true;
      getWebList(this.queryParams).then(response => {
        console.log(response)
        this.reportList = response.rows;
        this.loading = false;
      });
    },
    handleDiagnose(row){
      if(row.reportType.includes('JECGsingle')){
        this.$router.push({
          path: "/staticECG",
          query: {pId: row.pId, state: 1, queryParams: this.queryParams, ecgType: "JECGsingle"}
        });
      }
      else if(row.reportType.includes('JECG4')){
        this.$router.push({path: "/JECG4_ECG", query: {pId: row.pId,state:4,queryParams:this.queryParams,ecgType:"JECG4"}});
      }
      else if(row.reportType.includes('JECG12')){
        this.$router.push({path: "/restingECG", query: {pId:row.pId,state:12,queryParams:this.queryParams,ecgType:"JECG12"}});
      } else if (row.reportType.includes('week')){
        this.$router.push({path: "/week_report", query: {id:row.reportId}});
      }
      else{
        this.$message.warning('未知类型，请联系管理员')
      }


    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: null,
        patientPhone: null,
        diagnosisStatus: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParamsEcg.pageNum = 1;

      this.getListEcg();
    },
    handleQueryWeek() {
      this.queryParamsWeek.pageNum = 1;

      this.getListWeek();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: null,
        PatPhone: null,
        intelligentDiagnosis: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        diagnosisStatus: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex: null,
        pageNum: 1,
        pageSize: 10,
        isSelect: '2'
      }
      this.resetForm("queryForm");
      if (this.$auth.hasRole("doctorUser")&&!this.$auth.hasRole("admin")){
        this.queryParams.diagnosisStatus = '2'
      }
      this.handleQuery();
    },
    /** 重置按钮操作 */
    resetQueryWeek() {
      this.queryParamsWeek = {
        pageNum: 1,
        pageSize: 10,
        weekid: null,
        patientPhone: null,
        doctorPhone: null,
        patientName: null,
        doctorName: null,
        diagnosisStatus: 1,
        diagnosisConclusion: null
      }
      this.resetForm("queryFormWeek");
      this.handleQueryWeek();
    },
    getAgeTypeSection(age,heart){
      if(age<=1){
        return heart < 110 || heart > 130
      }else if (age<=3){
        return heart < 100 || heart > 120
      }else if (age<=7){
        return heart < 80 || heart > 100
      }else if (age>=8){
        return heart < 60 || heart > 100
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pId = row.pId || this.ids
      getPatient_management(pId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pId != null) {
            updatePatient_management(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatient_management(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    dialogFormVisibleVerify() {
      this.$refs["verifyForm"].validate(valid => {
        if (valid) {
          let obj = {
            accountPwd: this.verifyForm.password
          }
          getVerify(obj).then(r => {
            this.$modal.msgSuccess("密码正确");
            this.verifyForm.status = true
            this.dialogFormVisibleVerifyAuthority = false
            sessionStorage.setItem('isShowName', true)
            this.isShowName.status = !this.isShowName.status;
            this.isShowName.name = "隐藏信息"
          })
        }
      })
    },

    isShowNameClick() {

        if (this.isShowName.status) {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示信息"

        } else {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "隐藏信息"
        }


    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let isShowName = sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName) {
        const pIds = row.pId || this.ids;
        this.$modal.confirm('是否确认删除用户管理编号为"' + pIds + '"的数据项？').then(function () {
          return delPatient_management(pIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      } else {
        this.verifyForm.password = ''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    /** 导出按钮操作 */
    handleExport() {
      let isShowName = sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName) {
        this.download('patient_management/patient_management/export', {
          ...this.queryParams
        }, `patient_management_${new Date().getTime()}.xlsx`)
      } else {
        this.verifyForm.password = ''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    /**
     * 查看个人历史记录
     */
    lookHistoryData(val) {
      console.log(val)

      let daolian = val.ecgType.substring(0,5)
      console.log(daolian)
      // return
      if (daolian == 'JECGs' ){
        this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone, ecgType: "JECGsingle"}});
      }else if (daolian == "JECG1"){
        this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone, ecgType: "JECG12"}});
      }else if(daolian == "JECG4"){
        this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone, ecgType: "JECG4"}});
      }else {
        // this.$modal.msgError("没有此类型");
        this.$modal.msgSuccess("没有此类型");
      }
      // this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone, ecgType: "JECGsingle"}});
    },
    findJEcgReport(row){
      this.$router.push({path: "/jecg_report", query: {pId: row.pId}});
    },

    getMH(zdList , ecgType){
      let str = ''
      if (ecgType){
        ecgType.split(",").forEach(item => {
          if (this.canConvertToInt(item)){
            zdList.forEach(zd => {
              if (zd.value == item) {
                str += zd.label + ','
              }
            })
          }else {
            str += item + ','
          }
        })
        if (str.endsWith(",")){
          str = str.substring(0, str.length - 1)
        }
      }
      return str;
    },
    canConvertToInt(value) {
      // 尝试将值转换为整数
      const parsedInt = Number(value);
      // 判断转换后的值是否为整数且不为NaN
      return Number.isInteger(parsedInt);
    },
    getMH(zdList , ecgType){
      let str = ''
      if (ecgType){
        ecgType.split(",").forEach(item => {
          if (this.canConvertToInt(item)){
            zdList.forEach(zd => {
              if (zd.value == item) {
                str += zd.label + ','
              }
            })
          }else {
            str += item + ','
          }
        })
        if (str.endsWith(",")){
          str = str.substring(0, str.length - 1)
        }
      }
      return str;
    },
    getEcgType(text,type){
      try {
        let jsonString = text.replace(/nan/g, '0');
        jsonString = jsonString.replace(/'/g, '"');
        let obj = JSON.parse(jsonString)
        // 检查 obj 是否是对象，并获取指定类型的值
        if (obj && typeof obj === 'object') {
          // 如果 obj 对象中存在 type 属性，返回其值；否则返回 "--"
          return obj.hasOwnProperty(type) ? obj[type] : "--";
        } else {
          return "--";
        }
      } catch (error) {
        console.log(error)
        // 捕获异常并处理
        return "--"
      }
    },


    getBai(text){
      // re ( * 100).toFixed(1) + "%"
      return !isNaN(Number(text)) && isFinite(Number(text)) ? (Number(text) * 100).toFixed(1): "--"
    },
    lookHistoryData30(row) {
      let data = {
        row: row,
        ecgType: 1
      }
      //这样会使会话存储的东西越多，想用vuex但是不敢动。看到这里大哥别生气。
      // 在30天趋势图标签中，点击x号会自动的将会话存储中的/scatterPlot删除
      sessionStorage.setItem("/scatterPlot", JSON.stringify(data));
      this.$router.push({path: "/scatterPlot", query: {row: row, ecgType: 1}});
    },
    /** 查看心电图*/
    lookECG(row) {
      if(row.ecgType.includes('JECGsingle')){
        this.$router.push({
          path: "/staticECG",
          query: {pId: row.pId, state: 1, queryParams: this.queryParams, ecgType: "JECGsingle"}
        });
      }
      else if(row.ecgType.includes('JECG4')){
        this.$router.push({path: "/JECG4_ECG", query: {pId: row.pId,state:4,queryParams:this.queryParams,ecgType:"JECG4"}});
      }
      else if(row.ecgType.includes('JECG12')){
        this.$router.push({path: "/restingECG", query: {pId:row.pId,state:12,queryParams:this.queryParams,ecgType:"JECG12"}});}
      else{this.$message.warning('未知类型，请联系管理员')}
    },
    /** 生成报告*/
    handleInform(row) {
      let routeUrl = this.$router.resolve({path: "/restingECG", query: {pId: row.pId, hospitalName: row.hospitalName}});
      window.open(routeUrl.href, '_blank');

    },
    /** 下载报告*/
    downloadInform(row) {
      let routeUrl = this.$router.resolve({path: "/restingECG", query: {pId: row.pId, hospitalName: row.hospitalName}});
      window.open(routeUrl.href, '_blank');

    },
    downloadData(row) {
      console.log(row)
      let dizhi = row.ecgType.substring(0,5)
      // console.log(dizhi)
      // return
      if (dizhi == 'JECGs' ){
        window.open(`https://ecg.mindyard.cn:84/DECGReport/ECG_single/save/${row.pId}.dat`)
      }else if (dizhi == "JECG1"){
        window.open(`https://ecg.mindyard.cn:84/DECGReport/ECG/save/${row.pId}.dat`)
      }else if(dizhi == "JECG4"){
        window.open(`https://ecg.mindyard.cn:84/DECGReport/ECG4Lead/save/${row.pId}.dat`)
      }else {
        // this.$modal.msgError("没有此类型");
        this.$modal.msgSuccess("没有此类型");
      }
      // window.open(`https://ecg.mindyard.cn:84/DECGReport/ECG/save/${row.pId}.dat`)
    },
    handleweekReportUpdate(row){
      this.$router.push({path: "/week_report", query: {id:row.id}});
    }

  },
}
</script>
<style lang="scss" scoped>
.el-table .warning-row {
  background: #ffe7cf;
}

.el-table .success-row {
  background: rgba(250, 250, 250, 0.47);
}

.app-container {
  background-color: rgb(244, 244, 244);
  height: calc(100vh - 86px);
  display: flex;
  flex-direction: column;
  padding: 16px;
  overflow-y: scroll;
}

.searchForm {
  background-color: #fff;
  width: 100%;
  flex: 0;
}

.title {
  font-size: 24px;
  font-weight: 900;
  padding-top: 12px;
  padding-left: 24px;
  text-align: left;
  color: #000;
}

.elformbox {
  display: flex;
}

.form-left {
  width: 80%;
}

.form-left-hide {
  height: 49px;
  overflow: hidden;
}

.form-right {
  width: 20%;
  display: flex;
  justify-content: flex-end;
}

.tablebox {
  margin-top: 16px;
  background-color: #fff;
  flex: 1;
  padding: 0 16px;
  display: flex;
  flex-direction: column;
}

.table-hand {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;

  &-left {

  }

  &-right {
  }
}

.table-content {
  flex: 1;

  &-table {
    height: 100%;
  }
}

.table-hand-left-select {
  position: relative;

  ::v-deep .el-input__inner {
    text-align: right;
  }
}

.table-hand-left-select::after {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: 8px;
  font-size: 14px;
  font-weight: bold;
}

.table-hand-left-select-type::after {
  content: "采集类型";
}

.table-hand-left-select-status::after {
  content: "诊断状态";
}
</style>
<style scoped lang="scss">

::v-deep .el-tabs__item{
  color: #909399 !important;
}
::v-deep .is-active{
  color: #2e7dc9 !important;
}
</style>
