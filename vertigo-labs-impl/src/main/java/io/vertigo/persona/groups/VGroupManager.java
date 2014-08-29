package io.vertigo.persona.groups;

import io.vertigo.perona.users.VUser;

import java.util.List;

public interface VGroupManager {
	//void invit( )
	//pend
	void createGroup(VUser owner, String name);

	void addUser(VUser adder, VGroup group, VUser member);

	List<VGroup> getAllGroups();

	List<VGroup> getGroups();

	//	createGroup(groupId, name)
	//
	//	createGroup(groupId, name, description, permissionLevel)
	//	getAllGroups()
	//	getAllGroups(memberId)
	//	getDomain()
	//	getGroup(groupId)

}
