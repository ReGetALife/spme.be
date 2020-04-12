-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 139.199.75.41    Database: report
-- ------------------------------------------------------
-- Server version	5.7.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question` (
  `lab` varchar(20) NOT NULL,
  `step` int(10) NOT NULL,
  `question` varchar(400) DEFAULT NULL,
  `lower_lab` int(10) NOT NULL,
  `question_id` int(10) NOT NULL,
  PRIMARY KEY (`lab`,`step`,`lower_lab`,`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('CATALOG',1,'请问youidA.test这个数据集属于哪个编目?',3,1),('CATALOG',1,'VTOC里是什么内容？',6,1),('CATALOG',1,'请问所有的用户编目信息里包括了你的编目吗？',7,1),('CATALOG',1,'请问youridA.test在哪个用户编目里呢？',8,1),('CATALOG',1,'请问你是如何找到你的用户编目下的所有数据集呢？',9,1),('CATALOG',2,'使用LISTCAT USERCATALOG查看到了新的用户编目哪些信息？',1,1),('CATALOG',2,'使用LISTCAT ALIAS ALL查看到了用户编目哪些信息？',2,1),('CATALOG',2,'查看到了用户编目的哪些信息？',4,1),('CATALOG',2,'查看到了用户编目别名的哪些信息？',4,2),('CATALOG',2,'VVDS中存放的是什么内容？',5,1),('CATALOG',2,'请问所有的别名信息里有你的编目的别名吗？',7,1),('CATALOG',2,'你的用户编目的别名还存在吗？',9,1),('CATALOG',4,'查看到了用户编目的哪些信息？',4,1),('CATALOG',4,'查看到了用户编目别名的哪些信息？',4,2),('CATALOG',5,'新建youridA.xxx成功了吗？为什么？',4,1),('MVS',2,'你的 JCL 代码是什么？',1,1),('MVS',2,'执行 JCL 后的输出是什么？',1,2),('RACF',1,'What\'s group are you connect to?',1,1),('RACF',1,'Do you have any user attributes?',1,2),('RACF',1,'Do you hava any connect attributes to RACFLAB?',1,3),('RACF',1,'Do you have some class authorization?',1,4),('RACF',1,'新增 TSOxx01  for user Janet Smith',2,1),('RACF',1,'新增 TSOxx02  for user Robert Anderson',2,2),('RACF',1,'新增 TSOxx03  for user Leslie Brown',2,3),('RACF',1,'新增 TSOxx04  for user Arthur Fielding',2,4),('RACF',1,'新增 TSOxx05  for user Susan Johnson',2,5),('RACF',1,'TSOxx01(Janet Smith)：该管理员将对DIVxxADM组用户的安全进行管理，包括为用户重置密码，挂起和启用用户，实现的RACF命令是',3,1),('RACF',1,'该用户关联到哪些组？',3,2),('RACF',1,'该用户有哪些用户特权？',3,3),('RACF',1,'该用户是否有类权限（class authorization）',3,4),('RACF',1,'该用户关联到组DIVxxADM上时是否有什么特权（connect attributes）？',3,5),('RACF',1,'该用户关联到哪些组？',3,6),('RACF',1,'该用户有哪些用户特权？',3,7),('RACF',1,'该用户是否有类权限（class authorization）？',3,8),('RACF',1,'该用户关联到组DIVxxADM上时是否有什么特权（connect attributes）',3,9),('RACF',1,'该用户关联到哪些组？',3,10),('RACF',1,'该用户有哪些用户特权？',3,11),('RACF',1,'该用户是否有类权限（class authorization）？',3,12),('RACF',1,'该用户关联到组DIVxxADM上时是否有什么特权（connect attributes）？',3,13),('RACF',1,'保护题中五位用户的数据集',4,1),('RACF',1,'以TSOxx04登陆（FUNxxPRD组成员），创建 RESxxPRD.PROD.STUFF 数据集，是否成功？',5,1),('RACF',1,'什么给你权限让你能够为 RACF 定义新的 User Profile?',6,1),('RACF',1,'是否足以定义TSO段?',6,2),('RACF',1,'什么让你能定义 profiles 去控制 TSO 用户的登录过程?',6,3),('RACF',1,'是什么让您能够定义 profile 来控制对帐号的访问?',6,4),('RACF',1,'搜索yourid用户建立的所有组和用户',7,1),('RACF',1,'搜索yourid用户建立的所有用户',7,2),('RACF',1,'查看组内成员及其管理权限',7,3),('RACF',1,'搜索实验中所建立的保护数据集的Profile',7,4),('RACF',1,'查看某个数据集的保护策略（用户数据集及组数据集）',7,5),('RACF',1,'查看实验中所创建的通用资源TSO登陆过程(Class: TSOPROC)',7,6),('RACF',1,'查看实验中所创建的通用资源TSO账号(Class: ACCTNUM)的保护策略',7,7),('RACF',2,'定义DIVxxADM 用户管理组（相当于公司人事部门），RACF命令',1,1),('RACF',2,'定义DIVxxFUN 功能组（相当于公司各职能部门），后继实验将在该组下定义各个子功能组，RACF命令：',1,2),('RACF',2,'定义DIVxxRES 资源组（为有机组织和保护系统资源—包括数据集/CICS交易/系统和用户程序等资源—而设立的组），后继实验将在该组下定义各个子资源组，RACF命令：',1,3),('RACF',2,'为 Janet Smith(TSOxx01) 指定一个新的临时密码的RACF命令?',2,1),('RACF',2,'以TSOxx01身份登陆TSO，尝试修改用户密码等',3,1),('RACF',2,'以TSOxx02身份登陆TSO，将TSOxx01用户关联到FUNxxPRD和FUNxxTST',3,2),('RACF',2,'以TSOxx02身份登陆TSO，将TSOxx01从FUNxxPRD和FUNxxTST组中移走',3,3),('RACF',2,'PROFILE的ACCESS LIST是否有内容？, PROFILE的OWNER是谁？',4,1),('RACF',2,'以TSOxx03登陆（RESxxPRD组CREATE特权人员，即数据管理人员），为 RESxxPRD.DATA 创建一个全匹配的PROFILE进行保护',5,1),('RACF',2,'在DIVxxFUN下创建子组FUNxxAP',6,1),('RACF',2,'在DIVxxFUN下创建子组FUNxxSP',6,2),('RACF',2,'在DIVxxRES下创建子组RESxxTSO，用以管理TSO资源授权',6,3),('RACF',2,'总结本次实验体会及建议',7,2),('RACF',3,'定义FUNxxPRD 功能组，该组将用于对生产系统数据集(Production Data Sets)的访问进行集中授权（即如果该组对生产系统数据集有访问权限，该组的成员将自动继承这一权限）RACF命令：',1,1),('RACF',3,'定义FUNxxTST 功能组，该组将用于对测试系统数据集(Test Data Sets)的访问进行集中授权（即如果该组对测试系统数据集有访问权限，该组的成员将自动继承这一权限）',1,2),('RACF',3,'将 Arthur Fielding(TSOxx04)的帐号挂起，RACF命令是什么',2,1),('RACF',3,'总结本次实验体会及建议',3,1),('RACF',3,'对RESxxTST数据集进行题述保护的RACF指令',4,1),('RACF',3,'以TSOxx03登陆，把数据集PROFILE的Warning状态打开：把 RESxxTST.PROFILE 的Warning状态打开',5,1),('RACF',3,'新增SP用户TSOxx06, 新增AP用户TSOxx07',6,1),('RACF',4,'定义RESxxPRD 资源组，该组将用于保护生产系统的数据集。RACF命令：',1,1),('RACF',4,'定义RESxxTST 资源组，该组将用于保护测试系统的数据集。RACF命令：',1,2),('RACF',4,'Arthur Fielding(TSOxx04)出差回来，希望能够继续使用以前的帐号，RACF命令是什么',2,1),('RACF',4,'修改上面定义的 RESxxTST.PORFILE的访问列表，给FUNxxTST组赋予ALTER访问权限',4,1),('RACF',4,'以TSOxx04登陆，浏览‘RESxxTST.DATA’数据集，是否成功，是否收到什么系统信息？',5,1),('RACF',4,'为TSO用户创建一个新的登陆过程PROC#Sxx和PROC#Axx',6,1),('RACF',5,'利用RACF命令(Search)或者RACF面板查找组Profile。RACF命令：',1,1),('RACF',5,'请设置挂起日期为实验的第二天，启用日期为1个月后',2,1),('RACF',5,'定义RESxxPRD组数据集的RPOFILE，进行题述保护的RACF指令',4,1),('RACF',5,'以TSOxx03登陆，把‘RESxxTST.’PROFILE的Warning状态关闭',5,1),('RACF',5,'测试TSOxx06和TSOxx07登陆TSO，成功吗？出现什么信息？',6,1),('RACF',6,'实验总结',1,1),('RACF',6,'查看是否生效，考虑如何撤销',2,1),('RACF',6,'修改上面定义的RESxxPRD.PORFILE的访问列表，给FUNxxPRD组赋予ALTER访问权限',4,1),('RACF',6,'以TSOxx04登陆，浏览‘RESxxTST.DATA’数据集，是否成功？',5,1),('RACF',6,'创建通用资源TSOPROC的RPOFILE，保护AP和SP的TSO登陆过程',6,1),('RACF',6,'浏览PROC#Sxx和PROC#Axx PROFILE，它们用于保护不同的TSO登陆服务',6,2),('RACF',7,'使用Search命令查找以上新建的用户Profile',2,1),('RACF',7,'确定组数据集PROFIEL是否创建并按照预定的要求保护成功',4,1),('RACF',7,'RACF代码',5,1),('RACF',7,'刷新TSOPROC类在内存中的Profile',6,1),('RACF',8,'系统规定密码多长时间更换一次？',2,1),('RACF',8,'系统记录过去的密码吗？如果记录，记录多少个？',2,2),('RACF',8,'在密码过期之前系统会发送警告信息给用户吗？',2,3),('RACF',8,'系统有规定密码设置规则吗？',2,4),('RACF',8,'创建ALIAS：RESxxTST和RESxxPRD, 测试是否成功',4,1),('RACF',8,'检测哪一个PROFILE在保护‘RESxxPRD.NEWAPPL.FINANCE.DATA’和‘RESxxPRD.NEWAPPL.HR.DATA’',5,1),('RACF',8,'创建RPOFILE：ACCT#Sxx 该ACCTNUM为SP用户组提供TSO登陆服务',6,1),('RACF',8,'创建PROFILE：ACCT#Axx 该ACCTNUM为AP用户组提供TSO登陆服务',6,2),('RACF',8,'浏览PROFILE：ACCT#Sxx和ACCT#Axx',6,3),('RACF',9,'将用户Arthur Fielding(TSOxx04)连接到组FUNxxPRD，实现其对生产数据集的访问',2,1),('RACF',9,'将用户Susan Johnson (TSOxx05)连接到组FUNxxTST，实现其对测试数据集的访问',2,2),('RACF',9,'创建一个顺序数据集RESxxPRD.DATA (RECFM=FB, LRECL=80, VOLUME=USER01)',4,1),('RACF',9,'创建一个顺序数据集RESxxTST.DATA (RECFM=FB, LRECL=80, VOLUME=USER01)',4,2),('RACF',9,'检测一个Generic PROFILE  RESxxPRD. 保护了那些数据集',5,1),('RACF',9,'查看SP和AP用户是否拥有提交JCL作业的权利',6,1),('RACF',10,'使用什么RACF命令可以验证用户是否关联到组？',2,1),('RACF',10,'以TSOxx04用户登陆，编辑以RESxxPRD为HLQ的数据集(如RESxxPRD.DATA)，看是否成功，为什么？',4,1),('RACF',10,'一个组的group-special用户或者Create/Connect/Join用户是否能够直接访问（比如新建/更新）组文件？',5,1),('RACF',10,'保护TSOxx06的用户数据集, 保护TSOxx07的用户数据集',6,1),('RACF',11,'总结本次实验体会及建议',2,1),('RACF',11,'以TSOxx04用户登陆，执行RACF命令 \'PROFILE WTPMSG\'。然后编辑以RESxxTST为HLQ的数据集(如RESxxTST.DATA)，看是否成功，为什么？',4,1),('RACF',11,'一个组的group-special用户或者Create用户是对数据集的Profile有操作权限，还是对数据集本身有操作权限？',5,1),('RACF',11,'为TSOxx06创建别名\", \"测试是否成功, 为TSOxx07创建别名, 测试是否成功\"',6,1),('RACF',12,'以TSOxx05用户登陆TSO(从上面的实验中可以看出TSOxx05是FUNxxTST的成员)。对以RESxxTST为HLQ的数据集(如RESxxTST.DATA)进行编辑，测试对数据集的保护是否成功，为什么？',4,1),('RACF',12,'总结本次实验体会及建议',5,1),('RACF',12,'以TSOxx06和TSOxx07登陆TSO，测试是否成功',6,1),('RACF',12,'在登陆过程中，删除TSOPROC和ACCTNUM',6,2),('RACF',12,'在登陆过程中，键入不存在的TSOPROC和ACCTNUM',6,3),('RACF',12,'在登陆过程中，输入Region大小值大于4096，或者小于4096',6,4),('RACF',12,'以yourid登陆TSO，删除TSOxx06用户RPOFILE的TSO段，然后再尝试以TSOxx06登陆，看系统怎么反应？',6,5),('RACF',12,'为TSOxx06重新赋值TSO段',6,6),('RACF',12,'以yourid登陆TSO，取消TSOxx07用户RPOFILE的TSOPROC赋值或者ACCTNUM赋值，然后再尝试以TSOxx07登陆，看系统怎么反应？',6,7),('RACF',13,'以TSOxx01登陆，删除RESxxPRD打头的数据集(如RESxxPRD.DATA)，看是否成功？考虑为什么。',4,1),('RACF',13,'如果想为TSO资源的保护指定一个管理员，如何操作比较简单高效？',6,1),('RACF',14,'保留TSOxx01登陆的Session，再打开一个新的Session，以TSOxx03登陆TSO，修改 RESxxPRD.Profile，给TSOxx01赋ALTER权限',4,1),('RACF',14,'总结本次实验体会及建议',6,1),('RACF',15,'再尝试用TSOxx01用户删除RESxxPRD.DATA，看是否成功？',4,1),('RACF',16,'TSOxx01重新登陆后再尝试删除RESxxPRD.DATA，看是否成功？',4,1),('RACF',17,'总结本次实验体会及建议',4,1),('REXX',1,'实验 4：REXX 函数实验 I 的代码',1,1),('REXX',1,'运行结果',1,2),('REXX',1,'步骤2 REXX代码',2,1),('REXX',1,'运行结果',2,2),('REXX',1,'步骤1 REXX代码',3,1),('REXX',1,'运行结果',3,2),('REXX',1,'步骤1 REXX代码',4,1),('REXX',1,'运行结果',4,2),('REXX',1,'步骤1 JCL代码',5,1),('REXX',1,'运行结果',5,2),('REXX',1,'步骤1 REXX代码',6,1),('REXX',1,'运行结果',6,2),('REXX',1,'步骤4 JCL代码',7,1),('REXX',1,'运行结果',7,2),('SMS',1,'在SDSF面板中执行系统命令/D SMS，查看系统的SMS库信息',3,1),('SMS',1,'SMS使用的SCDS，ACDS，COMMDS控制数据集均是什么？',5,1),('SMS',2,'记录下列表中显示的数据集个数',2,1),('SMS',2,'该Storage Group在哪几个系统中可用？状态如何？',5,1),('SMS',2,'记录盘卷的空间使用情况',6,1),('SMS',3,'有多少数据集是顺序数据集（DS ORG=PS）？',2,1),('SMS',3,'数据集的名字等于或多于3段的有几个？',2,2),('SMS',3,'SMS中一共有几个Storage Group，他们可以用在哪几个系统中？状态如何？',5,1),('SMS',4,'该命令的作用是什么?',2,1),('SMS',4,'该Storage Group中有哪些盘卷？',5,1),('SMS',5,'隐藏全部数据集，然后恢复显示?',2,1),('SMS',5,'隐藏除了前2个数据集的其它数据集，然后恢复显示?',2,2),('SMS',5,'该卷可以在哪些系统中使用？状态如何？',5,1),('SMS',5,'盘卷空间成功释放之后，进入3.4，重新观察盘卷，记录空间使用情况并对比。',6,1),('SMS',6,'屏幕上显示的第一个条目是一个数据集吗？',1,1),('SMS',6,'以yourid作为HLQ的编目数据集一共有多少个？',1,2),('SMS',6,'哪些属性列是有内容的？这些属性列的信息是来自于VTOC还是CATALOG？',1,3),('SMS',6,'使用‘VIEW 3 4 10’，看看显示列表有何变化',2,1),('SMS',6,'使用‘VIEW *’，再看看显示列表有何变化',2,2),('SMS',6,'所有的数据集都成功转换了吗？‘是’还是‘不是’？',4,1),('SMS',6,'如果答案是‘不是’，那么哪些数据集没有转换成功？为什么没有转换成功？',4,2),('SMS',6,'ACS Routine是否对每一个转换成功的数据集赋予了一个Storage Class？请记录下Storage Class的名字。',4,3),('SMS',6,'ACS Routine是否对每一个转换成功的数据集赋予了一个Management Class？请记录下Management Class的名字。',4,4),('SMS',6,'该VARY命令的作用是什么？',5,1),('SMS',6,'总结本次实验体会及建议',6,1),('SMS',6,'总结本次实验体会及建议',7,1),('SMS',7,'在3.4中观察你的数据集都在哪些盘卷中，选择一个卷上所有以yourid作为HLQ的数据集，继续步骤6，新建一个列表（如下图），执行列表，思考VTOC里存放着数据集的哪些信息？',1,1),('SMS',7,'比较步骤6和步骤7的结果，说说VTOC和CATALOG中存放数据集信息的差别。',1,2),('SMS',7,'使用CATLIST行命令查看列表中某个顺序或分区数据集，该数据集所在的盘卷是什么？其编目信息存放在那个目录中？',2,1),('SMS',7,'使用CATLIST行命令查看列表中某个VSAM数据集，该VSAM数据集的数据部分叫什么？索引部分叫什么（如果存在的话）？是哪种类型的VSAM数据集？各部分所在的盘卷是什么？',2,2),('SMS',7,'总结本次实验体会及建议',4,1),('SMS',7,'如果系统目前只有该Storage Group（本例中采用了PRIMARY）可用，那么哪些盘卷可以用来分配新的数据集？',5,1),('SMS',8,'一共显示了多少个数据集，属性列内容来自于哪里（VTOC/ CATALOG）?',1,1),('SMS',8,'哪一个VOLUME空闲空间所占的比例最小?',2,1),('SMS',8,'哪一个VOLUME可用的分区（EXTENT）数目最多？',2,2),('SMS',8,'哪一个VOLUME拥有最大的分区？',2,3),('SMS',8,'目前SMS的Storage Group又发生了一些变化，如果系统目前仍然只有该Storage Group（本例中采用了PRIMARY）可用，那么哪些盘卷可以用来分配新的数据集？',5,1),('SMS',9,'‘Active’配置中定义了几个‘Data Class’？',2,1),('SMS',9,'有没有一个‘Data Class’可以用来新建一个记录长度为80的分区数据集？如果没有请在后面的实验中自己定义一个。',2,2),('SMS',9,'该卷在SMS下的状态如何？',5,1),('SMS',10,'一共显示了多少个SMS卷？',1,1),('SMS',10,'使用DISPLAY行命令，查看上个步骤显示的 ‘Data Class’的具体内容。',2,1),('SMS',10,'为什么该设备不被SMS管理？',5,1),('SMS',11,'为了正确显示系统的非SMS管理的物理卷， ‘Storage Group Name’和‘CDS Name’字段如何赋值？',1,1),('SMS',11,'系统中一共有多少个Volume？',1,2),('SMS',11,'哪些盘卷有超过50%的Free Space？',1,3),('SMS',11,'在ISMF主选项面板中选择‘5  Storage Class’，查看‘Active’配置中的‘Storage Class’的信息。使用DISPLAY行命令，查看每个‘Storage Class’的具体内容。',2,1),('SMS',11,'为什么UNIT参数是空的?',5,1),('SMS',11,'该卷的SMS状态是什么？',5,2),('SMS',11,'该卷目前可以用来新建数据集吗？',5,3),('SMS',12,'总结本次实验体会及建议',1,1),('SMS',12,'在ISMF主选项面板中选择‘6  Storage Group’，查看‘Active’配置中的‘Storage Group’信息。使用LISTVOL行命令，查看每个‘Storage Group’中的盘卷信息。',2,1),('SMS',12,'上图中重新上线的卷的SMS状态是什么？',5,1),('SMS',12,'该卷目前可以用来新建数据集吗？',5,2),('SMS',13,'配置中默认的‘Management Class’是什么？',2,1),('SMS',13,'输入数据集yourid.TEST.SDS，测试结果如何？',3,1),('SMS',13,'新建测试用例CASE2，输入数据集yourid.TEST.PDS，测试结果如何？',3,2),('SMS',13,'新建测试用例CASE3，输入数据集yourid.TEST.KSDS，测试结果如何？',3,3),('SMS',13,'哪些路径(PATH)可以用来访问该设备地址？',5,1),('SMS',14,'在ISMF主选项面板中选择‘3  Management Class’，查看‘Active’配置中的‘Management Class’的信息。使用DISPLAY行命令，查看上个步骤中得到的CDS基本配置中默认MC的内容。',2,1),('SMS',14,'该设备是由SMS控制的?',5,1),('SMS',15,'总结本次实验体会及建议',2,1),('SMS',15,'总结本次实验体会及建议',5,1),('SMS',17,'总结本次实验体会及建议',3,1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `report` (
  `lab` varchar(20) NOT NULL,
  `uid` varchar(10) NOT NULL,
  `answer` varchar(15000) DEFAULT NULL,
  `step` int(10) NOT NULL,
  `lower_lab` int(10) NOT NULL,
  `question_id` int(10) NOT NULL,
  `is_draft` varchar(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`lab`,`uid`,`step`,`lower_lab`,`question_id`),
  KEY `foreign_question_idx` (`lab`,`step`,`lower_lab`,`question_id`),
  CONSTRAINT `foreign_question` FOREIGN KEY (`lab`, `step`, `lower_lab`, `question_id`) REFERENCES `question` (`lab`, `step`, `lower_lab`, `question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES ('Catalog','ST000','还挺',2,1,1,'Y'),('Catalog','ST000','没看到诶\n',2,2,1,'Y'),('Catalog','ST000','不会啊',5,4,1,'Y'),('CATALOG','ST006','再次测试保存草稿',2,1,1,'Y'),('CATALOG','ST007','sdfdsf',2,1,1,'Y'),('CATALOG','ST007','dsfdsfsfdsfdsf',2,2,1,'Y'),('CATALOG','ST019','dwa',1,3,1,'Y'),('CATALOG','ST019','徐',2,1,1,'Y'),('CATALOG','ST019','ADW',2,2,1,'Y'),('CATALOG','ST019','是按时',2,4,1,'Y'),('CATALOG','ST019','asd',2,4,2,'Y'),('CATALOG','ST019','都是',4,4,1,'Y'),('CATALOG','ST019','是',4,4,2,'Y'),('CATALOG','ST021','adfdsfafd',1,3,1,'Y'),('CATALOG','ST021','safdfa',1,8,1,'Y'),('MVS','ST000','//MVSTRY JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,\n// NOTIFY=ST000\n//SDSF EXEC PGM=SDSF         \n//ISFOUT DD SYSOUT=*         \n//ISFIN DD *                                    \n  /D SMS                                       \n/* ',2,1,1,'Y'),('MVS','ST000','JESYSMSG\n ICH70001I ST000    LAST ACCESS AT 21:08:36 ON SUNDAY, APRIL 12, 2020\n IEF236I ALLOC. FOR MVSTRY SDSF\n IEF237I JES2 ALLOCATED TO ISFOUT\n IEF237I JES2 ALLOCATED TO ISFIN\n IEF142I MVSTRY SDSF - STEP WAS EXECUTED - COND CODE 0000\n IEF285I   ST000.MVSTRY.JOB08773.D0000102.?             SYSOUT\n IEF285I   ST000.MVSTRY.JOB08773.D0000101.?             SYSIN\n IEF373I STEP/SDSF    /START 2020103.2124\n IEF032I STEP/SDSF    /STOP  2020103.2124\n         CPU:     0 HR  00 MIN  00.14 SEC    SRB:     0 HR  00 MIN  00.00 SEC\n         VIRT:    12K  SYS:   284K  EXT:     3572K  SYS:    11024K\n         ATB- REAL:                     8K  SLOTS:                     0K\n              VIRT- ALLOC:       4M SHRD:       0M\n IEF375I  JOB/MVSTRY  /START 2020103.2124\n IEF033I  JOB/MVSTRY  /STOP  2020103.2124\n         CPU:     0 HR  00 MIN  00.14 SEC    SRB:     0 HR  00 MIN  00.00 SEC\nISFOUT\n1 HQX7790 -----------------  SDSF PRIMARY OPTION MENU  --------------------------\n  COMMAND INPUT ===>                                            SCROLL ===> PAGE\n\n  DA    Active users                      INIT  Initiators\n  I     Input queue                       PR    Printers\n  O     Output queue                      PUN   Punches\n  H     Held output queue                 RDR   Readers\n  ST    Status of jobs                    LINE  Lines\n                                          NODE  Nodes\n  LOG   System log                        SO    Spool offload\n  SR    System requests                   SP    Spool volumes\n  MAS   Members in the MAS                NS    Network servers\n  JC    Job classes                       NC    Network connections\n  SE    Scheduling environments           RM    Resource monitor\n  RES   WLM resources                     CK    Health checker\n  ENC   Enclaves                          LNK   Link list data sets\n  PS    Processes                         LPA   Link pack data sets\n  SYS   System information                APF   APF data sets\n                                          PAG   Page data sets\n  END   Exit SDSF                         PARM  Parmlib data sets\n                                          ULOG  User session log\n\n  Licensed Materials - Property of IBM\n\n  5650-ZOS Copyright IBM Corp. 1981, 2013.\n  US Government Users Restricted Rights - Use, duplication or\n  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.\n\n\n1 HQX7790 -----------------  SDSF PRIMARY OPTION MENU     COMMAND ISSUED\n  COMMAND INPUT ===>                                            SCROLL ===> PAGE\n  RESPONSE=BY01                                                                                                                     	\n   IGD002I 21:24:30 DISPLAY SMS 469                                                                                                 	\n   SCDS = SMS.PLEXY1.SCDS                                                                                                           	\n   ACDS = SMS.PLEXY1.ACDS                                                                                                           	\n   COMMDS = SMS.PLEXY1.COMMDS                                                                                                       	\n   ACDS LEVEL = z/OS V2.1                                                                                                           	\n   DINTERVAL = 150                                                                                                                  	\n   REVERIFY = NO                                                                                                                    	\n   ACSDEFAULTS = NO                                                                                                                 	\n       SYSTEM     CONFIGURATION LEVEL    INTERVAL SECONDS                                                                           	\n       PLEXY1     2020/04/12 21:24:29           15                                                                                  	\n  SE    Scheduling environments           RM    Resource monitor\n  RES   WLM resources                     CK    Health checker\n  ENC   Enclaves                          LNK   Link list data sets\n  PS    Processes                         LPA   Link pack data sets\n  SYS   System information                APF   APF data sets\n                                          PAG   Page data sets\n  END   Exit SDSF                         PARM  Parmlib data sets\n                                          ULOG  User session log\n\n  Licensed Materials - Property of IBM\n\n  5650-ZOS Copyright IBM Corp. 1981, 2013.\n  US Government Users Restricted Rights - Use, duplication or\n  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.\n',2,1,2,'Y'),('RACF','HHH','我是一个神秘的账号',1,1,1,'Y'),('RACF','HHH','',1,1,2,'Y'),('RACF','HHH','',1,1,3,'Y'),('RACF','HHH','',1,1,4,'Y'),('RACF','HHH','试一试',3,1,1,'Y'),('RACF','HHH','',3,1,2,'Y'),('RACF','IBMUSER','1',1,1,1,'Y'),('RACF','IBMUSER','2',1,1,2,'Y'),('RACF','ST000','',1,1,1,'Y'),('RACF','ST000','实验1的步骤1的2',1,1,2,'Y'),('RACF','ST000','牛逼35\n真的',1,1,3,'Y'),('RACF','ST000','',1,1,4,'Y'),('RACF','ST000','非常好的实验7',2,7,2,'Y'),('RACF','ST006','STGRP',1,1,1,'N'),('RACF','ST006','SPECIAL',1,1,2,'N'),('RACF','ST006','SPECIAL',1,1,3,'N'),('RACF','ST006','USER',1,1,4,'N'),('RACF','ST006','实验二的答案',1,2,1,'N'),('RACF','ST006','实验二的答案',1,2,2,'N'),('RACF','ST006','实验二的答案',1,2,3,'N'),('RACF','ST006','实验二的答案',1,2,4,'N'),('RACF','ST006','实验二的答案',1,2,5,'N'),('RACF','ST006','试试',1,3,1,'N'),('RACF','ST006','test',1,3,2,'N'),('RACF','ST006','不会',1,6,1,'N'),('RACF','ST006','不会',1,6,2,'N'),('RACF','ST006','HHHH',2,1,1,'N'),('RACF','ST006','222',2,4,1,'N'),('RACF','ST006','改天回答',3,4,1,'N'),('RACF','ST006','改天回答',4,4,1,'N'),('RACF','ST007','233',1,1,1,'N'),('RACF','ST007','2324234232',1,1,2,'N'),('RACF','ST007','4342423',1,1,3,'N'),('RACF','ST007','2342423423',1,1,4,'N'),('RACF','ST007','sf',2,1,1,'N'),('RACF','ST007','sdfsd',2,1,2,'N'),('RACF','ST007','fsdfsdf',2,1,3,'N'),('RACF','ST007','8888',2,2,1,'N'),('RACF','ST012','改天回答',1,1,1,'Y'),('RACF','ST012','改天回答',1,1,2,'Y'),('RACF','ST017','123',1,1,1,'Y'),('RACF','ST017','23',1,1,2,'Y'),('RACF','ST017','34',1,1,3,'Y'),('RACF','ST017','45',1,1,4,'Y'),('RACF','ST017','test1',2,1,1,'Y'),('RACF','ST017','test2',2,1,2,'Y'),('RACF','ST017','test3',2,1,3,'Y'),('RACF','ST017','AG',3,1,1,'Y'),('RACF','ST017','1212',3,1,2,'Y'),('RACF','ST019','改天回答',1,1,1,'Y'),('RACF','ST019','改天回答',1,1,2,'Y'),('RACF','ST021','改天回答',1,1,1,'Y'),('RACF','ST021','改天回答',1,1,2,'Y'),('RACF','ST021','改天回答',1,1,3,'Y'),('RACF','ST021','改天回答',1,1,4,'Y'),('RACF','ST021','dfsf',1,4,1,'Y'),('RACF','ST021','改天回答',2,1,1,'Y'),('RACF','ST021','改天回答',2,1,2,'Y'),('RACF','ST021','改天回答',2,1,3,'Y'),('RACF','ST021','dfsd',2,4,1,'Y'),('RACF','ST021','改天回答',3,1,1,'Y'),('RACF','ST021','改天回答',3,1,2,'Y'),('RACF','ST021','改天回答',3,4,1,'Y'),('RACF','ST021','改天回答',4,1,1,'Y'),('RACF','ST021','改天回答',4,1,2,'Y'),('RACF','ST021','改天回答',5,1,1,'Y'),('RACF','ST021','改天回答',6,1,1,'Y'),('REXX','ST000','jjjjjj\nkkk\nk\nk\nk\nk',1,2,1,'Y'),('REXX','ST000','jjjjjj',1,2,2,'Y'),('REXX','ST000','好的\n挺好',1,3,1,'Y'),('REXX','ST000','',1,3,2,'Y'),('REXX','ST025','123',1,1,1,'Y'),('REXX','ST025','这里显示返回结果...',1,1,2,'Y'),('REXX','ST025','123',1,2,1,'Y'),('REXX','ST025','这里显示返回结果...',1,2,2,'Y'),('REXX','ST025','SAY 2',1,3,1,'Y'),('REXX','ST025','123',1,5,1,'Y'),('REXX','ST025','a',1,6,1,'Y'),('SMS','IBMUSER','1',6,1,1,'Y'),('SMS','IBMUSER','2',6,1,2,'Y'),('SMS','IBMUSER','3',6,1,3,'Y'),('SMS','ST000','',3,2,1,'Y'),('SMS','ST000','',3,2,2,'Y'),('SMS','ST000','好哒',6,1,1,'Y'),('SMS','ST000','',6,1,2,'Y'),('SMS','ST000','',6,1,3,'Y'),('SMS','ST000','还好',6,6,1,'Y'),('SMS','ST000','步骤13？？',13,3,1,'Y'),('SMS','ST000','',13,3,2,'Y'),('SMS','ST000','',13,3,3,'Y'),('SMS','ST000','好的',14,2,1,'Y'),('SMS','ST006','123',6,1,1,'Y'),('SMS','ST007','DDDD',1,3,1,'N'),('SMS','ST007','99999',2,2,1,'N'),('SMS','ST007','777',3,2,1,'N'),('SMS','ST007','444',3,2,2,'N'),('SMS','ST007','是',6,1,1,'N'),('SMS','ST007','1',6,1,2,'N'),('SMS','ST007','catlog',6,1,3,'N'),('SMS','ST007','sdfdsf',6,4,1,'N'),('SMS','ST007','fdsfsd',6,4,2,'N'),('SMS','ST007','fdsf',6,4,3,'N'),('SMS','ST007','fdsf',6,4,4,'N'),('SMS','ST007','有作用就行',6,5,1,'N'),('SMS','ST007','好',13,3,1,'N'),('SMS','ST007','好',13,3,2,'N'),('SMS','ST007','好',13,3,3,'N'),('SMS','ST007','d\'d\'d\'d',17,3,1,'N'),('SMS','ST021','dsafdf12312312313',2,2,1,'Y'),('SMS','ST021','sdfdf',3,2,1,'Y'),('SMS','ST021','fasdfadsfa',3,2,2,'Y'),('SMS','ST021','dsf',6,1,1,'Y'),('SMS','ST021','sfs',6,1,2,'Y'),('SMS','ST021','sfds',6,1,3,'Y'),('SMS','ST021','是不是',6,4,1,'Y'),('SMS','ST021','有点东西',6,4,2,'Y'),('SMS','ST021','',6,4,3,'Y'),('SMS','ST021','',6,4,4,'Y'),('SMS','ST021','233',7,1,1,'Y'),('SMS','ST021','233',7,1,2,'Y');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `result` (
  `uid` varchar(10) NOT NULL,
  `score` int(10) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `lab` varchar(20) NOT NULL,
  `is_release` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`lab`),
  KEY `for_question_idx` (`lab`),
  CONSTRAINT `for_question` FOREIGN KEY (`lab`) REFERENCES `question` (`lab`),
  CONSTRAINT `for_student` FOREIGN KEY (`uid`) REFERENCES `student` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` VALUES ('ST000',66,'太棒了还不错的哦，我就是要给你一个非常长的评论','racf',1),('ST000',50,'不行','REXX',1),('ST000',100,'很棒哦','SMS',1),('ST006',60,'bad bad','racf',1),('ST007',10,'bad','racf',1);
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `uid` varchar(10) NOT NULL,
  `sid` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`),
  UNIQUE KEY `sid_UNIQUE` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('GAOZHEN','1000000','高老师'),('IBMUSER','1000001','ibmuser'),('ST000','1000002','测试账号'),('ST001','1652600','小明'),('ST002','1652601','小红'),('ST006','1652000','小明'),('ST007','1650257','陈雨蕾'),('ST009','1652666','徐仁和'),('ST012','1652717','杨言'),('ST017','1652749','KrisHuang'),('ST019','1652759','王芮臻'),('ST021','1652775','李庆国'),('ST025','1652805','徐伟喆');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teacher` (
  `id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='A table that store teachers of the class.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('GAOZHEN'),('IBMUSER'),('ST000'),('ST007'),('ST021');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 22:03:13
