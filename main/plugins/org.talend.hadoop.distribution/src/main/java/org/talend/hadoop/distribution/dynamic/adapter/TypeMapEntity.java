// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.List;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class TypeMapEntity implements IVariable {

    public static final String ATTR_TYPE = "type"; //$NON-NLS-1$

    public static final String ATTR_MODULE_GROUPS = "moduleGroups"; //$NON-NLS-1$

    private String type;

    private List<GroupWithConditionBean> moduleGroups;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GroupWithConditionBean> getModuleGroups() {
        return this.moduleGroups;
    }

    public void setModuleGroups(List<GroupWithConditionBean> moduleGroups) {
        this.moduleGroups = moduleGroups;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object value = null;
        switch (variable) {
        case ATTR_MODULE_GROUPS:
            value = getModuleGroups();
            break;
        case ATTR_TYPE:
            value = getType();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support to get variable from " + variable + ", you can implement to support it.");
        }
        return value;
    }

}
