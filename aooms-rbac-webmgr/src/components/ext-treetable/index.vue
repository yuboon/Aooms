<template>
    <el-table :data="formatData" :row-style="showRow" v-bind="$attrs">
        <el-table-column align="left" >
            <template slot-scope="scope">
                <span v-for="space in scope.row._level" :key="space" class="ms-tree-space"/>
                <span v-if="iconShow(0,scope.row)" class="tree-ctrl" @click="toggleExpanded(scope.$index)">
                    <i v-if="!scope.row._expanded" class="el-icon-caret-right ms-treetable-icon" />
                    <i v-else class="el-icon-caret-bottom ms-treetable-icon"/>
                </span>
                <span v-else><i class="fa fa-circle-o ms-treetable-icon"></i></span>

                {{ scope.row[rowKey] }}
                <!--{{ scope.$index }}-->
            </template>
        </el-table-column>

        <!--
        <el-table-column v-for="(column, index) in columns" :key="column.prop" :label="column.label" :width="column.width">
            <template slot-scope="scope">
                <span v-for="space in scope.row._level" v-if="index === 0" :key="space" class="ms-tree-space"/>
                <span v-if="iconShow(index,scope.row)" @click="toggleExpanded(scope.$index)">
                    <i v-if="!scope.row._expanded" class="el-icon-caret-right ms-treetable-icon"/>
                    <i v-else class="el-icon-caret-bottom ms-treetable-icon"/>
                </span>
                <span v-else-if="index===0"><i class="fa fa-circle-o ms-treetable-icon"></i></span>

                <span class="tree-ctrl">
                    <span v-if="index === 0"><i :class="scope.row.icon"></i></span>
                    <span style="padding-left: 5px;">{{ column.formatter ? column.formatter(scope.row,column.prop,scope.row[column.prop],scope.$index) : scope.row[column.prop] }}</span>
                </span>
            </template>
        </el-table-column>
        -->

        <slot/>

    </el-table>
</template>

<script>

    import treeToArray from "./tree";
    export default {
        props: {
            data: {
                type: [Array, Object],
                required: true
            },
            rowKey:{

            },
            /*columns: {
                type: Array,
                default: () => []
            },*/
            evalFunc: Function,
            evalArgs: Array,
            expandAll: {
                type: Boolean,
                default: false
            }
        },
        computed: {
            // 格式化数据源
            formatData: function() {
                let tmp = this.data;

                /*const func = this.evalFunc || treeToArray;
                const args = this.evalArgs
                    ? Array.concat([tmp, this.expandAll], this.evalArgs)
                    : [tmp, this.expandAll];
                func.apply(null, args);*/

                return treeToArray(tmp, false);
            }
        },
        methods: {
            showRow: function(row) {
                /*const show = row.row.parent
                    ? row.row.parent._expanded && row.row.parent._show
                    : true;
                row.row._show = show;*/

                let show = false;
                var isRoot = row.row.parent;
                if(!isRoot){
                    show = row.row._show;
                }else{
                    //console.log(row.row.resource_name + ",parentShow:" + row.row.parent._show + ",expand:" + row.row.parent._expanded + ",show:" + row.row._show);
                    show = row.row.parent._show && row.row.parent._expanded && row.row._show;
                }

                /*const show = (row.row.parent)
                    ? row.row.parent._show && row.row._show
                    : true;*/

                //let show = (row.row._show && row.row.parent._show);
                /*let isRoot = typeof(row.row.parent) == "undefined";
                if(isRoot){
                    show = true;
                }else{
                    show = row.row._show;
                    //show = (row.row._show && row.row.parent._expanded && row.row.parent._show);
                }*/

                return show
                    ? "animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s;"
                    : "display:none;";
            },
            // 切换下级是否展开
            toggleExpanded: function(trIndex) {
                const record = this.formatData[trIndex];
                record._expanded = !record._expanded;

                record.children.forEach(item => {
                    item._show = !item._show;
                });
            },
            // 图标显示
            iconShow(index, record) {
                return index === 0 && record.children && record.children.length > 0;
            },

            // 添加节点
            append(data, parentRow) {

                if(!parentRow._level){
                    this.$set(parentRow,"_level",0);
                }

                if(!parentRow.children){
                    this.$set(parentRow,"children",[]);
                }

                this.$set(data,"_show",true);
                this.$set(data,"_expanded",false);
                this.$set(data,"_level",parentRow._level + 1);
                this.$set(data,"children",[]);
                var children = parentRow.children.push(data);

                this.$set(parentRow,"_expanded",true);
            },

            // 删除节点
            remove(row) {
                //row._show = false;
                //row.$set(row, 'resource_name' , 'delete');
                if(row.parent){
                    var children = row.parent.children;
                    var index = 0;
                    children.forEach(item => {
                        if(row.id == item.id){
                            children.splice(index, 1);
                        }
                        index++;
                    });
                }else{
                    var index = 0;
                    this.data.forEach(item => {
                        if(row.id == item.id){
                            this.data.splice(index, 1);
                        }
                        index++;
                    });
                }
            },

            // 显示节点
            show(row,isVisible) {

            },

            // 显示节点
            filter(key,val) {
                function search(key,val,list){
                    if(!list) return;

                    // 没有值的情况
                    if(!val){
                        list.forEach(item => {
                            item._show = true;

                            if(item.parent){
                                item._show = item.parent._show && item.parent._expanded;
                            }

                            if(item.children){
                                search(key, val ,item.children);
                            }
                        });
                    }else{
                        list.forEach(item => {
                            var isFind = false;
                            key.forEach(v => {
                                if(item[v].indexOf(val) != -1){
                                    isFind = true;
                                }
                            })
                            if(isFind){
                                item._show = true;
                                var parent = item.parent;
                                while(parent){
                                    parent._show = true;
                                    parent._expanded = true;
                                    parent = parent.parent;
                                }

                                // 找到并且存在子节点，收起来
                                if(item.children){
                                    item._expanded = false;
                                    function expanded(list){
                                        if(!list) return;
                                        list.forEach(item => {
                                            item._expanded = false;
                                            expanded(item.children);
                                        });
                                    }
                                    expanded(item.children);
                                }
                            }else{
                                item._show = false;
                                //item._expanded = false;
                            }

                            search(key, val ,item.children);
                        });
                    }
                }

                search(key,val,this.data);
            }
        }
    };
</script>
<style rel="stylesheet/css">
    @keyframes treeTableShow {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
    @-webkit-keyframes treeTableShow {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
</style>

<style scoped>
    .ms-tree-space {
        position: relative;
        top: 1px;
        display: inline-block;
        font-style: normal;
        font-weight: 400;
        line-height: 1;
        width: 18px;
        height: 14px;
    }
    .ms-tree-space::before {
        content: "";
    }

    .ms-treetable-icon{
        color: #606266 !important;
        padding: 0 5px;
        cursor: pointer;
    }

    .processContainer {
        width: 100%;
        height: 100%;
    }

    table td {
        line-height: 26px;
    }

    .tree-ctrl {
        color: #606266; /*#2196f3;*/
    }
</style>
