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
package org.talend.hadoop.distribution.dynamic;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.hadoop.distribution.dynamic.adapter.ClassLoaderBean;
import org.talend.hadoop.distribution.dynamic.adapter.TemplateBean;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicClassLoaderExtensionAdaper extends DynamicExtensionAdapter {

    public static final String TAG_NAME = "extension"; //$NON-NLS-1$

    public static final String ATTR_POINT = "org.talend.core.runtime.classloader_provider"; //$NON-NLS-1$

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    public DynamicClassLoaderExtensionAdaper(TemplateBean templateBean, DynamicConfiguration configuration,
            Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap) {
        super(templateBean, configuration);
        this.moduleGroupBeanAdapterMap = moduleGroupBeanAdapterMap;
    }

    public IDynamicExtension adapt(IProgressMonitor monitor) throws Exception {
        resolve();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        String repository = templateBean.getRepository();
        configuration.setRemoteRepositoryUrl(repository);

        String distributionName = templateBean.getDistribution();
        String id = templateBean.getId();

        IDynamicExtension classLoaderExtension = DynamicFactory.getInstance().createDynamicExtension();
        classLoaderExtension.setExtensionId(DynamicDistributionUtils.getPluginKey(distributionName, id, ATTR_POINT));
        classLoaderExtension.setExtensionPoint(ATTR_POINT);

        List<ClassLoaderBean> classLoaders = templateBean.getClassLoaders();
        if (classLoaders != null) {
            for (ClassLoaderBean classLoader : classLoaders) {
                DynamicClassloaderAdapter classLoaderAdapter = new DynamicClassloaderAdapter(templateBean, configuration,
                        classLoader, moduleGroupBeanAdapterMap);
                IDynamicConfiguration dynamicClassLoader = classLoaderAdapter.adapt(monitor);
                classLoaderExtension.addConfiguration(dynamicClassLoader);
            }
        }

        return classLoaderExtension;
    }

    @Override
    protected void resolve() throws Exception {
        setResolved(true);
    }
}
