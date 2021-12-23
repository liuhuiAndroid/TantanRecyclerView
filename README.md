# TantanRecyclerView
仿探探左滑右滑效果实现

github上star较多的实现方式：https://github.com/yuqirong/CardSwipeLayout
https://my.oschina.net/sfshine/blog/1604007

使用 ItemTouchHelper 来处理 ItemView 的触摸滑动事件：https://www.jianshu.com/p/2b124787d727

RecyclerView 回收复用机制
起点：RecyclerView 的 onTouchEvent 的 move 事件
重点：tryGetViewHolderForPositionByDeadline()
博客：https://www.jianshu.com/p/40820ea48457