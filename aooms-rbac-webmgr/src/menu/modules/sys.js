export default {
    path: '/sys',
    title: '系统管理',
    iconSvg: 'd2-crud',
    children: (pre => [
        { path: `${pre}/index`, title: '首页', icon: 'home' },
        { path: `${pre}/user`, title: '用户管理', icon: 'home' },
    ])('/sys')
}
