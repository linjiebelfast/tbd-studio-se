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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.MavenPlugin;
import org.talend.maven.aether.DependencyNode;
import org.talend.maven.aether.DynamicDistributionAetherUtils;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbstractDependencyResolver implements IDependencyResolver {

    private DynamicConfiguration configuration;

    @Override
    public DependencyNode collectDependencies(String groupId, String artifactId, String scope, String classifier,
            IProgressMonitor monitor) throws Exception {
        String version = getDependencyVersionByHadoopVersion(groupId, artifactId, monitor);

        DependencyNode node = collectDependencies(groupId, artifactId, version, scope, classifier, monitor);
        return node;
    }

    @Override
    public DependencyNode collectDependencies(String groupId, String artifactId, String version, String scope, String classifier,
            IProgressMonitor monitor) throws Exception {
        String remoteRepositoryUrl = configuration.getRemoteRepositoryUrl();
        String localRepositoryPath = getLocalRepositoryPath();
        DependencyNode node = DynamicDistributionAetherUtils.collectDepencencies(remoteRepositoryUrl, localRepositoryPath,
                groupId, artifactId, version, classifier, scope, monitor);
        return node;
    }

    public DynamicConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(DynamicConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getLocalRepositoryPath() {
        return MavenPlugin.getMaven().getLocalRepositoryPath();
    }
}
