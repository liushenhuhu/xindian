<template>
  <div class="app-container">
    <div class="patient-info">
      <div class="title">基本信息</div>
      <el-skeleton :loading="infoLoading">
        <template slot="template">
          <el-skeleton-item></el-skeleton-item>
        </template>
        <template>
          <el-row>
            <el-col offset="6">
              <div class="patient-info-item basic-info">{{ patientInfo.patientName || '' }}</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col offset="6">
              <div class="patient-info-item basic-info info-minor">{{ patientInfo.patientSex || '' }}
                {{ patientInfo.patientAge || '' }}岁
              </div>
            </el-col>
          </el-row>
          <el-row class="patient-info-item mg-top">
            <el-col :span="6" class="patient-info-item-name">患者管理ID:</el-col>
            <el-col :span="18">{{ patientInfo.pId || '' }}</el-col>
          </el-row>
          <el-row class="patient-info-item">
            <el-col :span="6" class="patient-info-item-name">联系方式:</el-col>
            <el-col :span="18">{{ phoneEncrypt(patientInfo.patientPhone) || '' }}</el-col>
          </el-row>
          <el-row class="patient-info-item">
            <el-col :span="6" class="patient-info-item-name">身份证号:</el-col>
            <el-col :span="18">{{ patientInfo.patientCode || '' }}</el-col>
          </el-row>
          <el-row class="patient-info-item">
            <el-col :span="6" class="patient-info-item-name">就诊医院:</el-col>
            <el-col :span="18">{{ patientInfo.hospitalName || '' }}</el-col>
          </el-row>
          <div class="title title-2">紧急联系人 联系电话</div>
          <el-row class="patient-info-item" v-if="!!patientInfo.familyPhone">
            <el-col :span="5">{{ patientInfo.familyName || '未命名' }}</el-col>
            <el-col :span="18" :offset="1">{{ patientInfo.familyPhone || '' }}</el-col>
          </el-row>
          <el-row class="patient-info-item" v-else>
            <el-col :offset="4">无</el-col>
          </el-row>
        </template>
      </el-skeleton>
    </div>
    <div class="patient-data">
      <div class="patient-data-above">
        <div class="title">预警统计图</div>
        <div class="patient-data-above-cvs">
        </div>
      </div>
      <div class="patient-data-below">
        <div class="patient-data-below-hand">
          <div class="title">预警日志
            <el-date-picker
              v-model="daterangeLogTime"
              style="width: 240px;margin-left:8px;"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="getAlertLogList"
            ></el-date-picker>
          </div>
          <div>
            <el-select style="width:100px;" v-model="logQueryParams.anoStatus" placeholder="是否标注" clearable
                       @change="getAlertLogList">
              <el-option :key="2" label="全部" :value="null"/>
              <el-option :key="1" label="是" :value="1"/>
              <el-option :key="0" label="否" :value="0"/>
            </el-select>
          </div>
        </div>
        <div class="patient-data-below-table">
          <el-table v-loading="tableLoading" :data="alertLogList" height="tableHeight" style="height: 100%">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="发生时间" align="center" prop="logTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.logTime, '{y}--{m}--{d} {h}:{i}:{s}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="预警类型" align="center" min-width="150" prop="logType"/>
            <el-table-column label="事件名称" align="center" min-width="150" prop="eventName"/>
            <el-table-column label="事件说明" align="center" min-width="150" prop="eventDescription"/>
            <el-table-column label="是否标注" align="center" min-width="150" prop="anoStatus">
              <template slot-scope="scope">
                <!--<dict-tag :options="dict.type.if_status" :value="scope.row.anoStatus"/>-->
                <el-tag :type="scope.row.anoStatus?'':'info'">{{ scope.row.anoStatus ? '是' : '否' }}</el-tag>
              </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width"
                             fixed="right">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleLook(scope.row)"
                >查看日志
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import {list12Alert_log} from "@/api/DRearly/DRearly";
import {listAlert_log, getAlert_log, delAlert_log, addAlert_log, updateAlert_log} from "@/api/alert_log/alert_log";

export default {
  name: "alert_patient",
  data() {
    return {
      infoLoading: true,
      tableLoading: true,
      tableHeight: 0,
      dRearlyQueryParams: {
        pId: null,
        ecgType: null,
      },
      daterangeLogTime: [],
      logQueryParams: {
        pageNum: 1,
        pageSize: 10,
        logId: null,
        logTime: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        pId: null,
        patientName: null,
        hospitalName: null,
        anoStatus: null,
        ecgType: null,
      },
      opName: '',
      patientInfo: null,
      alertLogList: [],
      alertLogTotal: 0,
    }
  },
  async created() {
    await this.getDearlyList()
    await this.getAlertLogList()
    console.log('mounted')

    window.onresize = function () {
      this.echartsDraw();
    }
  },
  activated() {
    this.getDearlyList()
    this.getAlertLogList()
    this.updateTableHeight()
  },
  async mounted() {
    await this.getDearlyList()
    await this.getAlertLogList()
    console.log('mounted')
    this.updateTableHeight()
  },
  methods: {
    async getDearlyList() {
      this.infoLoading = true;
      this.dRearlyQueryParams.pId = this.$route.query.pId;
      this.dRearlyQueryParams.ecgType = this.$route.query.state;
      this.opName = this.dRearlyQueryParams.ecgType == 12 ? '单人12导预警' : '单人单导预警';
      let {data: res} = await list12Alert_log(this.dRearlyQueryParams);
      this.patientInfo = res;
      this.infoLoading = false;
      if (res.alertLogList.length == 0) {
        this.patientInfo.alertLogList = null;
        this.echartsDraw();

      } else {
        this.echartsDraw();
      }

      // this.loading=false;
      //r.data.alertLogList是数据
    },
    phoneEncrypt(phone){
      //返回前三位和八个*
      return phone.replace(/(\d{3})\d{8}(\d{0,3})/, '$1********$2')
    },
    /**
     * 更新表格高度
     */
    updateTableHeight() {
      let appContainerBox = document.querySelector('.patient-data-below');
      let defaultHeight = appContainerBox.offsetHeight;
      defaultHeight -= (16 + 16);
      this.tableHeight = defaultHeight;
      console.log('高度', this.tableHeight)
      this.$forceUpdate();
    },
    async getAlertLogList() {
      console.log("请求开始")
      this.logQueryParams.pId = this.$route.query.pId;
      this.logQueryParams.logType = this.$route.query.type;
      this.logQueryParams.ecgType = this.$route.query.ecgType;
      // console.log(this.queryParams)
      this.tableLoading = true;
      this.logQueryParams.params = {};
      if (null != this.daterangeLogTime && '' != this.daterangeLogTime) {
        this.logQueryParams.params["beginLogTime"] = this.daterangeLogTime[0];
        this.logQueryParams.params["endLogTime"] = this.daterangeLogTime[1];
      }
      if (this.logQueryParams.logType === 'null') {
        this.logQueryParams.logType = null;
      }
      if (this.logQueryParams.ecgType === 'null') {
        this.logQueryParams.ecgType = null;
      }

      let res = await listAlert_log(this.logQueryParams)
      console.log('请求成功')
      console.log(res)
      this.alertLogList = res.rows;
      this.alertLogTotal = res.total;
      this.tableLoading = false;
    },
    echartsDraw() {
      let cvsDom = document.querySelector('.patient-data-above-cvs')
      let cvs = this.$echarts.init(cvsDom)
      let option = {
        title: {
          show: !this.patientInfo.alertLogList,
          textStyle: {
            color: 'black',
            fontSize: 24,
          },
          text: '暂无数据',
          left: "center",
          top: "center"
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          show: !!this.patientInfo.alertLogList,
          orient: "vertical",
          icon: 'inherit',
          left: "right",
          top: "middle",
          padding: [5, 30, 5, 5],
          itemWidth: 36,
          itemHeight: 18,
          textStyle: {
            fontSize: 18
          },
        },
        series: [
          {
            name: this.opName,
            type: 'pie',
            center: ["30%", "50%"],
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: 'outside',
              fontSize: 24,
              borderWidth: 0,
              padding: [5, 5, 5, 5],
              // formatter: function (arg) {
              //   return arg.name + '：预警' + arg.value + "次"
              // }
            },
            emphasis: {},
            labelLine: {
              show: false
            },
            data: this.patientInfo.alertLogList,
          }
        ]
      }
      cvs.off('click')
      cvs.setOption(option)
      cvs.on('click', function (params) {
        th.$router.push({
          path: "log",
          query: {pId: this.dRearlyQueryParams.pId, type: params.name, ecgType: this.$route.query.state}
        });
      });
    },
    /**
     * 查看日志
     */
    handleLook(row) {
      // console.log(row);
      // console.log(row.logId);
      // this.$router.push({
      //   name: "lookLog",
      //   params: {logId: row.logId, logType: row.logType}
      // });
      // console.log(this.queryParams);
      // return
      this.$router.push({
        path: "/lookLog1",// name: "lookLog"
        query: {
          logId: row.logId,
          // logType: row.logType,
          userId: 0,
          state: this.$route.query.state,
          ecgType: this.logQueryParams.ecgType,
          pageNum: this.logQueryParams.pageNum,
          pageSize: this.logQueryParams.pageSize,
          isSuspected: this.logQueryParams.isSuspected,
          anoStatus: this.logQueryParams.anoStatus,
          queryParams: this.logQueryParams,
        }
      });
    },
  },
}
</script>
<style scoped lang="scss">
.app-container {
  background-color: #F4F4F4FF;
  height: calc(100vh - 86px);
  display: flex;
  justify-content: space-between;
  padding: 20px 24px;
}

.patient-info {
  padding: 16px;
  background-color: #fff;
  height: 100%;
  width: 24%;
}

.title {
  font-weight: bold;
  line-height: 24px;
  font-size: 16px;
  font-family: "PingFang SC";
  text-align: left;
  margin: 0;
}

.patient-data {
  height: 100%;
  width: 74%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  &-above {
    height: 44%;
    background-color: #fff;
    padding: 16px 20px;

    &-cvs {
      height: calc(100% - 24px);
    }
  }

  &-below {
    height: 54%;
    background-color: #fff;
    padding: 16px 8px 16px 20px;
    display: flex;
    flex-direction: column;

    &-hand {
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    &-table {
      flex: 1;
    }
  }
}

.patient-info-item {
  margin-top: 8px;
  &-name{
    color: #818181;
  }
}

.basic-info {
  font-size: 24px;
}

.basic-info:first-child {
  margin-top: 24px;
}

.info-minor {
  font-size: 20px;
  color: #666;
}

.mg-top{
  margin-top:40px;
}

.title-2 {
  margin-top: 40px;
}
</style>
