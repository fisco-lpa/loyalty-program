package util

//audit object
type AuditObject struct {
	CreateTime string //创建时间
	CreateUser string //创建人
	UpdateTime string //修改时间
	UpdateUser string //修改人
}

//账户信息记录对象
type Account struct {
	AccountId      string //账户ID
	UserId         string // 账户所属用户ID
	AccountBalance string // 账户积分剩余数量
	AccountTypeId  string // 账户类型ID
	Status         string // 状态
	OperFlag       string // 操作标积 0-新增，1-修改，2-删除
}

//积分交易记录对象
type PointsTransaction struct {
	TransId        string //积分交易ID
	RolloutAccount string //转出账户
	RollinAccount  string //转入账户
	TransAmount    string //交易积分数量
	Description    string //描述
	TransferTime   string //交易时间
	TransferType   string //交易类型
	OperFlag       string // 操作标积 0-新增，1-修改，2-删除
}

//积分交易明细对象
type PointsTransactionDetail struct {
	DetailId         string //逐笔明细流水号
	SourceDetailId   string //来源流水号
	TransId          string //积分交易ID
	RolloutAccount   string //转出账户
	RollinAccount    string //转入账户
	TransAmount      string //交易积分数量
	ExpireTime       string //过期时间
	ExtRef           string //外部引用
	Status           string // 状态  0-冻结，1-正常
	CurBalance       string //当笔积分剩余数量
	TrueransferTime  string //交易时间
	CreditCreateTime string //授信创建时间
	CreditParty      string //授信方账户
	Merchant         string // 商户账户
	OperFlag         string // 操作标积 0-新增，1-修改，2-删除
}
