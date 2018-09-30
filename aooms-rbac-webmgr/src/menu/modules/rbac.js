export default {
    path: '/rbac',
    title: '系统管理',
    iconSvg: 'd2-crud',
    children: (pre => [
        { path: `${pre}/index`, title: '首页', icon: 'home' },
        { path: `${pre}/user`, title: '用户管理', icon: 'home' },
        { path: `${pre}/org`, title: '机构管理', icon: 'home' },
        { path: `${pre}/module`, title: '模块管理', icon: 'home' }
    ])('/rbac')
}
