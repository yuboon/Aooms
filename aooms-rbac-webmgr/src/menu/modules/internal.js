export default {
    path: '/internal',
    title: '内置功能',
    iconSvg: 'd2-crud',
    children: [
        { path: `/rbac/user-info`, title: '个人信息', icon: 'home' },
        { path: `/rbac/user-password`, title: '密码修改', icon: 'home' }
    ]
}
