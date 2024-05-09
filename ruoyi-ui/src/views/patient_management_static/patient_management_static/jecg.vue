<template>
  <div class="app-container">
    <!--<router-view v-if="isRouterAlive"></router-view>-->
    <!--
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">

      <el-form-item label="用户姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入用户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户性别" prop="patientSex">
        <el-select placeholder="请选择性别" v-model="queryParams.patientSex">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户电话" prop="patientPhone">
        <el-input
          v-model="queryParams.PatPhone"
          placeholder="请输入用户电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="采集类型" prop="patientCode">
        <el-select v-model="queryParams.ecgType" placeholder="请选择采集类型">
          <el-option
            v-for="item in ecgList"
            :key="item.label"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
            <el-form-item label="用户管理编号" prop="pId">
              <el-input
                v-model="queryParams.pId"
                placeholder="请输入用户管理编号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
      <el-form-item label="医院名称" prop="hospitalName">
        <el-select v-model="queryParams.hospitalCode" placeholder="请选择医院代号">
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
          v-model="queryParams.equipmentCode"
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
              <el-select v-model="queryParams.onlineStatus" placeholder="请选择在线状态" clearable>
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
          v-model="queryParams.intelligentDiagnosis"
          placeholder="请输入智能诊断"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="诊断状态" prop="diagnosisStatus">
        <el-select v-model="queryParams.diagnosisStatus" placeholder="请选择诊断状态" clearable>
          <el-option
            v-for="dict in dict.type.diagnosis_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
           <el-form-item label="诊断结论" prop="diagnosisConclusion">
              <el-input
                v-model="queryParams.diagnosisConclusion"
                placeholder="请输入诊断结论"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="诊断医生" prop="diagnosisDoctor">
              <el-input
                v-model="queryParams.diagnosisDoctor"
                placeholder="请输入诊断医生"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
      <el-form-item label="风险等级" prop="ecgLevel">
        <el-select v-model="queryParams.ecgLevel" placeholder="请选择风险等级" clearable>
          <el-option
            v-for="dict in dict.type.ecg_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="社区医生" prop="doctorPhone">
        <el-select v-model="queryParams.doctorPhone" placeholder="请选择社区医生">
          <el-option
            v-for="item in option2"
            :key="item.doctorId"
            :label="item.doctorName"
            :value="item.doctorPhone">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户管理id" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入用户管理id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称模糊查询" prop="isSelect">
        <el-radio-group v-model="queryParams.isSelect">
          <el-radio label="1">开启</el-radio>
          <el-radio label="2">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
-->
    <div class="searchForm">
      <div class="title">信息查询</div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="100px" class="elformbox">
          <div class="form-left" :class="{'form-left-hide':!showSearch}">
            <el-form-item label="用户姓名" prop="patientName">
              <el-input
                v-model="queryParams.patientName"
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
              <el-select placeholder="请选择性别" v-model="queryParams.patientSex">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="用户管理id" prop="pId">
              <el-input
                v-model="queryParams.pId"
                placeholder="请输入用户管理id"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="用户电话" prop="patientPhone">
              <el-input
                v-model="queryParams.PatPhone"
                placeholder="请输入用户电话"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="医院名称" prop="hospitalName">
              <el-select v-model="queryParams.hospitalCode" placeholder="请选择医院代号">
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
                v-model="queryParams.equipmentCode"
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
              <el-select v-model="queryParams.onlineStatus" placeholder="请选择在线状态" clearable>
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
                v-model="queryParams.intelligentDiagnosis"
                placeholder="请输入智能诊断关键词"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="诊断状态" prop="diagnosisStatus">
              <el-select v-model="queryParams.diagnosisStatus" placeholder="请选择诊断状态" clearable>
                <el-option
                  v-for="dict in dict.type.diagnosis_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="诊断结论" prop="diagnosisConclusion">
              <el-input
                v-model="queryParams.diagnosisConclusion"
                placeholder="请输入诊断结论"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="诊断医生" prop="diagnosisDoctor">
              <el-input
                v-model="queryParams.diagnosisDoctor"
                placeholder="请输入诊断医生"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="风险等级" prop="ecgLevel">
              <el-select v-model="queryParams.ecgLevel" placeholder="请选择风险等级" clearable>
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

    <!--
    <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['patient_management:patient_management:add']"
              >新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['patient_management:patient_management:edit']"
              >修改
              </el-button>
            </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['patient_management:patient_management:export']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['patient_management:patient_management:export']"
        >导出
        </el-button>

      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-view"
          size="mini"
          @click="isShowNameClick"
        >{{ isShowName.name }}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getUpdateList"></right-toolbar>
    </el-row>-->

    <div class="tablebox">
      <div class="table-hand">
        <div class="table-hand-left">
          <el-select v-model="queryParams.ecgType" placeholder="请选择采集类型" clearable @change="getList"
                     class="table-hand-left-select table-hand-left-select-type">
            <el-option key="JECG" label="全部" value="JECG"></el-option>
            <el-option
              v-for="item in ecgList"
              :key="item.label"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="queryParams.diagnosisStatus" placeholder="选择诊断状态" clearable @change="getList"
                     class="table-hand-left-select table-hand-left-select-status" style="margin-left:8px">
            <el-option :key="null" label="全部" :value="null"></el-option>
            <el-option
              v-for="dict in dict.type.diagnosis_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value">
            </el-option>
          </el-select>
        </div>
        <div class="hand-right">
          <el-button type="text" size="small" icon="el-icon-refresh" @click="refreshList">刷新</el-button>
          <el-button type="danger"
                     plain
                     icon="el-icon-delete"
                     size="mini"
                     :disabled="multiple"
                     @click="handleDelete"
                     v-hasPermi="['patient_management:patient_management:export']"
          >删除</el-button>
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['patient_management:patient_management:export']">导出</el-button>
          <el-button
            type="success"
            plain
            icon="el-icon-view"
            size="mini"
            @click="isShowNameClick">{{isShowName.name}}</el-button>
        </div>
      </div>
      <div class="table-content">
        <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange"
                  highlight-current-row @current-change="handleCurrentChange" height="100%" class="table-content-table">
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
          <el-table-column label="用户管理ID" align="center" prop="pId" width="200"></el-table-column>
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
          <el-table-column label="医院" align="center" prop="hospitalName" min-width="200">
            <template slot-scope="scope">
              <span v-if="isShowName.status===true">{{ scope.row.hospitalName }}</span>
              <span v-else>***************</span>
            </template>
          </el-table-column>
          <!--      <el-table-column label="用户症状" align="center" prop="patientSymptom" show-overflow-tooltip/>-->
          <!--      <el-table-column label="医院名称" align="center" prop="hospitalName"/>-->
          <!--      <el-table-column label="报告时间" align="center" prop="reportTime" width="100" >-->
          <!--        <template slot-scope="scope">-->
          <!--          <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <el-table-column label="风险等级" align="center" prop="ecgLevel">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ecg_level" :value="scope.row.ecgLevel"/>
            </template>
          </el-table-column>
          <el-table-column label="心电种类" align="center" prop="ecgType" width="140">
            <template slot-scope="scope">
              <el-tag>
                {{ scope.row.ecgType }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="是否标注预警状态" width="150" align="center" prop="ecgIsLabel">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.if_status" :value="scope.row.ecgIsLabel"/>
            </template>
          </el-table-column>
          <!--
          <el-table-column label="是否标注预警状态" align="center" prop="ecgIsLabel">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.if_status" :value="scope.row.ecgIsLabel"/>
            </template>
          </el-table-column>-->

          <el-table-column label="诊断状态" align="center" prop="diagnosisStatus" width="100">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="诊断医生" align="center" width="100" prop="diagnosisDoctor">
          </el-table-column>
          <el-table-column label="设备号" align="center" prop="equipmentCode" min-width="150"></el-table-column>
          <el-table-column label="用户电话" align="center" prop="patientPhone" min-width="150">
            <template slot-scope="scope">
              <span v-if="isShowName.status===true">{{ scope.row.patientPhone }}</span>
              <span v-else>***************</span>
            </template>
          </el-table-column>
          <el-table-column label="家属电话" align="center" prop="familyPhone" min-width="150">
            <template slot-scope="scope">
              <span v-if="isShowName.status===true">{{ scope.row.familyPhone }}</span>
              <span v-else>***************</span>
            </template>
          </el-table-column>
          <el-table-column label="医生电话" align="center" prop="doctorPhone" min-width="150"></el-table-column>
          <el-table-column label="用户身份证号" align="center" prop="patientCode" min-width="200">
            <template slot-scope="scope">
              <span v-if="isShowName.status===true">{{ scope.row.patientCode }}</span>
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
                v-hasPermi="['patient:patient:alert']"
              >查看报告
              </el-button>
              <el-popover placement="left" width="50" trigger="click" style="margin-left:8px">
                <el-button
                  style="margin-left: 10px;"
                  size="mini"
                  type="text"
                  icon="el-icon-s-operation"
                  @click="selectECG(scope.row)"
                  v-hasPermi="['patient_management:patient_management:diagnose']"
                  v-if="!scope.row.diagnosisStatus"
                >选择医生诊断
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-s-order"
                  @click="lookHistoryData(scope.row)"
                >查看历史数据
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
                  icon="el-icon-download"
                  @click="downloadData(scope.row)"
                  v-hasPermi="['patient:patient:inform']"
                >下载数据
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
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['patient_management:patient_management:export']"
                >删除
                </el-button>
                <el-button
                  slot="reference"
                  size="small"
                  type="text"
                  v-hasPermi="['patient_management:patient_management:inform']">
                  更多<i class="el-icon-arrow-down"/>
                </el-button>
              </el-popover>

            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!-- 添加或修改用户管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入用户电话"/>
        </el-form-item>
        <el-form-item label="医院代号" prop="hospitalCode">
          <el-input v-model="form.hospitalCode" placeholder="请输入医院代号"/>
        </el-form-item>
        <el-form-item label="设备号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入设备号"/>
        </el-form-item>
        <el-form-item label="连接时间" prop="connectionTime">
          <el-date-picker clearable
                          v-model="form.connectionTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择连接时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="心电种类" prop="ecgType">
          <el-select v-model="form.ecgType" placeholder="请选择心电种类">
            <el-option
              v-for="dict in dict.type.ecg_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="在线状态" prop="onlineStatus">
          <el-select v-model="form.onlineStatus" placeholder="请选择在线状态">
            <el-option
              v-for="dict in dict.type.monitoring_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="智能诊断" prop="intelligentDiagnosis">
          <el-input v-model="form.intelligentDiagnosis" placeholder="请输入智能诊断"/>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-radio-group v-model="form.ecgLevel">
            <el-radio
              v-for="dict in dict.type.ecg_level"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="医生电话" prop="doctorPhone">
          <el-input v-model="form.doctorPhone" placeholder="请输入医生电话"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
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
import {
  listPatient_management,
  getPatient_management,
  delPatient_management,
  addPatient_management,
  updatePatient_management, updateStatus, getUserInfo, sendMsgToPatient, listDoc, docUpdate, getEcgType, redisAddData
} from "@/api/patient_management/patient_management";
import axios from "axios";
import $ from "jquery";
import {updateEquipmentStatus} from "@/api/equipment/equipment";
import {updateOnlineAll} from "@/api/online/online";
import {listHospitalId} from "@/api/hospital/hospital";
import {getVerify} from "@/api/verify/verify";
import {docList} from "@/api/doctor/doctor";

export default {
  name: "Patient_management_static/jecg",
  provide() {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  dicts: ['if', 'sex', 'monitoring_status', 'ecg_type', 'diagnosis_status', 'ecg_level', 'hospital_name_list', 'if_status'],
  data() {
    return {
      currentRow:null,
      currentScrollPos: 0,
      isRouterAlive: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      reportList: {
        pId: null,
        dPhone: null,
        hospital: null,
      },
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
      queryParams: {
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
        diagnosisStatus: null,
        intelligentDiagnosis: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex: null,
        isSelect: '2'
      },
      // 表单参数
      form: {},
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
      }
    };
  },

  beforeCreate() {
    // updateOnlineAll();
    console.log('beforeCreate')
  },
  activated() {
    console.log('activated')
    document.documentElement.scrollTop = this.currentScrollPos || 0
  },

  beforeRouteLeave(to, from, next) {
    this.currentScrollPos = document.documentElement.scrollTop || 0
    next()
  },

  created() {
    getEcgType(this.ecgType).then(r => {
      this.ecgList = r.data
    })
    listHospitalId(null).then(r => {
      this.options = r.rows
    })
    docList().then(q => {
      this.option2 = q.data
    })
    this.getList();
  },
  mounted() {
    console.log('mounted')
  },
  methods: {
    hideMiddleName(patientName) {
      if (patientName.length <= 1) {
        return "*"; // 一个字的则用一个 * 代替
      } else if (patientName.length === 2) {
        return patientName.charAt(0) + "*"; // 两个字的保留第一个字，后面用 * 代替
      } else {
        let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 2) + patientName.charAt(patientName.length - 1);
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
      this.reportList.pId = row.pId
      this.dialogFormVisible = true;
      listDoc().then(r => {
        console.log(r)
        this.option = r.data
      })
    },
    dialogForm() {
      this.reportList.dPhone = this.value[1]
      this.reportList.hospital = this.value[0]
      console.log(this.reportList)
      docUpdate(this.reportList).then(r => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
        this.dialogFormVisible = false;
      })
    },


    sendMsg(row) {
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
      let patientPhone = row.patientPhone
      if (row.patientPhone.length === 14 || row.patientPhone.length === 15) {
        patientPhone = row.patientPhone.substring(0, 11)
      }

      var isPhoneNumberValid = reg.test(patientPhone);
      if (isPhoneNumberValid) {
        console.log(patientPhone)
        console.log("用户电话: " + row.patientPhone)
        console.log("用户姓名: " + row.patientName)
        this.$confirm('向该用户发送短信提示采集存在较大干扰?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sendMsgToPatient(patientPhone).then(response => {
            this.$message({
              type: 'success',
              message: '发送成功!'
            });
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      } else {
        this.$message.error('该用户手机号不合法！！！');
      }
    },


    getUpdateList() {
      redisAddData(this.ecgType).then(r => {
        this.getList()
      })
    },


    /** 查询用户管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeConnectionTime && '' != this.daterangeConnectionTime) {
        this.queryParams.params["beginConnectionTime"] = this.daterangeConnectionTime[0];
        this.queryParams.params["endConnectionTime"] = this.daterangeConnectionTime[1];
      }
      if (this.queryParams.ecgType == null) {
        this.queryParams.ecgType = this.ecgType
      }
      listPatient_management(this.queryParams).then(response => {
        console.log(response)
        this.patient_managementList = response.rows;
        // 原先提交过的预警类型response.rows[].logDataType
        this.total = response.total;
        this.loading = false;
        if (this.queryParams.ecgType === 'JECGsingle') {
          this.queryParams.ecgType = null
        }
      });
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
      this.queryParams.pageNum = 1;

      this.getList();
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
      this.handleQuery();
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
      let isShowName = sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName) {
        if (this.isShowName.status) {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示信息"

        } else {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "隐藏信息"
        }
      } else {
        this.verifyForm.password = ''
        this.dialogFormVisibleVerifyAuthority = true
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
      this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone, ecgType: "JECGsingle"}});
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
      return
      // console.log(this.queryParams);
      this.$router.push({
        path: "/staticECG",
        query: {pId: row.pId, state: 1, queryParams: this.queryParams, ecgType: "JECGsingle"}
      });
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
      window.open(`https://ecg.mindyard.cn:84/DECGReport/ECG_single/save/${row.pId}.dat`)
    },
  }
};
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
