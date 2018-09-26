<template>
  <div>
    <el-form
      :inline="true"
      size="mini"
      class="demo"
    >

      <el-button type="primary" size="mini" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete">删除</el-button>

    </el-form>

    <el-table
      :data="currentTableData"
      v-loading="loading"
      size="mini"
      stripe
      style="width: 100%;"
      :height="tableHeight"
      @selection-change="handleSelectionChange">

     <!-- Table 展开行 -->
     <el-table-column type="expand" width="20">
        <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="商品名称">
                    <span>{{ props.row.key }}</span>
                </el-form-item>
                <el-form-item label="所属店铺">
                    <span>{{ props.row.value }}</span>
                </el-form-item>
                <el-form-item label="商品 ID">
                    <span>{{ props.row.scope }}</span>
                </el-form-item>
                <el-form-item label="店铺 ID">
                    <span>{{ props.row.adminNote }}</span>
                </el-form-item>
                <el-form-item label="商品分类">
                    <span>{{ props.row.type }}</span>
                </el-form-item>
                <el-form-item label="店铺地址">
                    <span>{{ props.row.admin }}</span>
                </el-form-item>
                <el-form-item label="商品描述">
                    <span>{{ props.row.dateTimeUse }}</span>
                </el-form-item>
            </el-form>
        </template>
    </el-table-column>

      <el-table-column
        type="selection"
        width="30">
      </el-table-column>

      <el-table-column label="卡密" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.key}}
        </template>
      </el-table-column>

      <el-table-column label="面值" width="60" align="center">
        <template slot-scope="scope">
          <el-tag
            size="mini"
            type="success">
            {{scope.row.value}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="50" align="center">
        <template slot-scope="scope">
          <boolean-control
            :value="scope.row.type"
            @change="(val) => {
              handleSwitchChange(val, scope.$index)
            }">
            <d2-icon
              name="check-circle"
              style="font-size: 20px; line-height: 32px; color: #67C23A;"
              slot="active"/>
            <d2-icon
              name="times-circle"
              style="font-size: 20px; line-height: 32px; color: #F56C6C;"
              slot="inactive"/>
          </boolean-control>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="50" align="center">
        <template slot-scope="scope">
          <boolean-control-mini
            :value="scope.row.type"
            @change="(val) => {
              handleSwitchChange(val, scope.$index)
            }">
            <d2-icon
              name="check-circle"
              style="font-size: 20px; line-height: 32px; color: #67C23A;"
              slot="active"/>
            <d2-icon
              name="times-circle"
              style="font-size: 20px; line-height: 32px; color: #F56C6C;"
              slot="inactive"/>
          </boolean-control-mini>
        </template>
      </el-table-column>

      <el-table-column label="管理员" width="60">
        <template slot-scope="scope">
          {{scope.row.admin}}
        </template>
      </el-table-column>

      <el-table-column label="管理员备注" :show-overflow-tooltip="true" width="100">
        <template slot-scope="scope">
          {{scope.row.adminNote}}
        </template>
      </el-table-column>

      <el-table-column label="创建时间" width="150" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.dateTimeCreat}}
        </template>
      </el-table-column>

      <el-table-column label="使用状态" align="center">
        <template slot-scope="scope">
          <el-tag
            size="mini"
            :type="scope.row.used ? 'info' : ''">
            {{scope.row.used ? '已使用' : '未使用'}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="使用时间" width="150" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.dateTimeUse}}
        </template>
      </el-table-column>

        <el-table-column fixed label="操作" align="center" width="100">
            <template slot-scope="scope">
                <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
                <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.$index, scope.row)"></el-button>

                <!--<el-button
                        type="primary" plain
                        size="mini"
                        icon="el-icon-edit"
                        @click="handleEdit(scope.$index, scope.row)"></el-button>
                <el-button
                        size="mini"
                        type="danger"
                        icon="el-icon-delete"
                        @click="handleEdit(scope.$index, scope.row)"></el-button>-->
            </template>
        </el-table-column>

    </el-table>

    <!-- 弹窗 -->
    <data-form ref="dataForm"></data-form>

  </div>


</template>

<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>

<script>
import BooleanControl from './BooleanControl.vue'
import BooleanControlMini from './BooleanControlMini.vue'
import DataForm from './DataForm.vue'

export default {
  components: {
    BooleanControl,
    BooleanControlMini,
    DataForm
  },
  props: {
    tableData: {
      default: () => []
    },
    loading: {
      default: false
    }
  },
  data () {
    return {
      currentTableData: [],
      multipleSelection: [],
      downloadColumns: [
        { label: '卡密', prop: 'key' },
        { label: '面值', prop: 'value' },
        { label: '状态', prop: 'type' },
        { label: '管理员', prop: 'admin' },
        { label: '管理员备注', prop: 'adminNote' },
        { label: '创建时间', prop: 'dateTimeCreat' },
        { label: '使用状态', prop: 'used' },
        { label: '使用时间', prop: 'dateTimeUse' }
      ],
      isShow:false,
      tableHeight:window.innerHeight - 300
    }
  },
  watch: {
    tableData: {
      handler (val) {
        this.currentTableData = val
      },
      immediate: true
    }
  },
  mounted () {
    this.$nextTick(() => {
        let self = this;
        window.onresize = function() {
            //self.height = self.$refs.table.$el.offsetHeight
            self.tableHeight = window.innerHeight - 300;
        }
    })
  },
  methods: {
    handleSwitchChange (val, index) {
      const oldValue = this.currentTableData[index]
      this.$set(this.currentTableData, index, {
        ...oldValue,
        type: val
      })
      // 注意 这里并没有把修改后的数据传递出去 如果需要的话请自行修改
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    downloadDataTranslate (data) {
      return data.map(row => ({
        ...row,
        type: row.type ? '禁用' : '正常',
        used: row.used ? '已使用' : '未使用'
      }))
    },
    handleDownloadXlsx (data) {
      this.$export.excel({
        title: 'D2Admin 表格示例',
        columns: this.downloadColumns,
        data: this.downloadDataTranslate(data)
      })
        .then(() => {
          this.$message('导出表格成功')
        })
    },
    handleDownloadCsv (data) {
      this.$export.csv({
        title: 'D2Admin 表格示例',
        columns: this.downloadColumns,
        data: this.downloadDataTranslate(data)
      })
        .then(() => {
          this.$message('导出CSV成功')
        })
    },
    add:function(){
        this.$refs.dataForm.open({});
    },
    handleEdit:function(index,row){
        this.$refs.dataForm.open({
            name: '张三',
            region: 'shanghai',
            date1: '2018-09-09',
            date2: "20:09:10",
            delivery: true,
            type: ["1","2"],
            resource: '2',
            desc: '描述信息',
            email:'337422@qq.com'
        });
    },
    handleDelete:function(index,row){
        var self = this;
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            var len = self.multipleSelection.length;
            alert(len);
            this.$message({
                type: 'success',
                message: '成功删除'+ len +'条数据'
            });

            this.$notify({
                title: '成功',
                message: '这是一条成功的提示消息',
                type: 'success'
            });
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除'
            });
        });
    }
  }
}
</script>
