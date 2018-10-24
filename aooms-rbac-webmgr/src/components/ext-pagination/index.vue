<template>

    <el-pagination
            :current-page="current"
            :page-size="size"
            :total="total"
            :page-sizes="[3, 200, 300, 400]"
            layout="total, sizes, prev, pager, next, jumper"
            style="margin: -10px;"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
    </el-pagination>

</template>

<script>
    export default {
        data() {
            return {
                current: 1,
                size:  3,
                total: 0
            }
        },
        methods: {
            handleSizeChange(val) {
                this.size = val;
                this.$emit('change', {
                    current: this.current,
                    size: val,
                    total: this.total
                })
            },
            handleCurrentChange(val) {
                this.current = val;
                this.$emit('change', {
                    current: val,
                    size: this.size,
                    total: this.total
                })
            }
        }
    }
</script>
