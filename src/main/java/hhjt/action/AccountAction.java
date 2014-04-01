package hhjt.action;

import java.util.List;
import java.util.Map;

import hhjt.bean.Account;
import hhjt.bean.Message;
import hhjt.bean.Order;
import hhjt.service.AccountService;
import hhjt.service.MessageService;
import hhjt.service.OrderService;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope(value="prototype")
public class AccountAction{

	@Resource
	private AccountService actService;
	@Resource
	private MessageService msgService;
	@Resource
	private OrderService orderService;
	
	private String username;
	private String password;
	private Account account;
	private List<Message> recvMsgs;
	private List<Message> sendMsgs;
	private int accountId;
	private Order order;
	private int ticketId;
	private List<Order> orders;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String login(){
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		Account act=actService.login(username, password);
		if(act==null) return "input";
		Map session=ActionContext.getContext().getSession();
		session.put("account",act);
		loadSendMsgs();
		loadRecvMsgs();
		listOrder();
		return "success";
	}
	
	public String register(){
		
		if(!actService.checkName(username))
			return "input";
		actService.register(account);
		return "success";
		
	}
	
	public void loadRecvMsgs(){
		Map session=ActionContext.getContext().getSession();
		Account act=(Account) session.get("account");
		recvMsgs=msgService.getRecvMsg(act.getId());
	}
	
	public void loadSendMsgs(){
		Map session=ActionContext.getContext().getSession();
		Account act=(Account) session.get("account");
		sendMsgs=msgService.getSendMsg(act.getId());
	}
	
	public void listOrder(){
		Map session=ActionContext.getContext().getSession();
		Account act=(Account) session.get("account");
		orders=orderService.listOrders(act.getId());
	}
	
	public String reqEmpower(){
		
		Map session=ActionContext.getContext().getSession();
		Account act=(Account) session.get("account");
		msgService.sendMsg(act.getId(), 14, "������Ȩ");
		loadSendMsgs();
		loadRecvMsgs();
		listOrder();
		return "success";
	}
	
	public String order(){
		Map session=ActionContext.getContext().getSession();
		Account act=(Account) session.get("account");
		orderService.addOrder(order,ticketId,act.getId());
		loadSendMsgs();
		loadRecvMsgs();
		listOrder();
		return "success";
	}
	
	public String empower(){
		
		actService.setLevel(accountId, 1);
		loadSendMsgs();
		loadRecvMsgs();
		listOrder();
		return "success";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountService getActService() {
		return actService;
	}

	public void setActService(AccountService actService) {
		this.actService = actService;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public MessageService getMsgService() {
		return msgService;
	}

	public void setMsgService(MessageService msgService) {
		this.msgService = msgService;
	}

	public List<Message> getRecvMsgs() {
		return recvMsgs;
	}

	public void setRecvMsgs(List<Message> recvMsgs) {
		this.recvMsgs = recvMsgs;
	}

	public List<Message> getSendMsgs() {
		return sendMsgs;
	}

	public void setSendMsgs(List<Message> sendMsgs) {
		this.sendMsgs = sendMsgs;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}