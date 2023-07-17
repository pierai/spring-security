/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.config.annotation;

/**
 * Allows for configuring a {@link SecurityBuilder}. All {@link SecurityConfigurer} first
 * have their {@link #init(SecurityBuilder)} method invoked. After all
 * {@link #init(SecurityBuilder)} methods have been invoked, each
 * {@link #configure(SecurityBuilder)} method is invoked.
 *
 * @param <O> The object being built by the {@link SecurityBuilder} B
 *               SecurityBuilder B正在生成的对象
 * @param <B> The {@link SecurityBuilder} that builds objects of type O. This is also the
 * {@link SecurityBuilder} that is being configured.
 * @author Rob Winch
 * @see AbstractConfiguredSecurityBuilder
 * @泛型 此类有两个类型参数，一个是O；另一个是SecurityBuilder&lt;O&gt;及其子类，我们给它取个别名，叫B。<O, B extends SecurityBuilder<O>>
 *     简单地说，也就是当前这个类可以创建O对象，并对其进行配置，
 */
public interface SecurityConfigurer<O, B extends SecurityBuilder<O>> {

	/**
	 * Initialize the {@link SecurityBuilder}. Here only shared state should be created
	 * and modified, but not properties on the {@link SecurityBuilder} used for building
	 * the object. This ensures that the {@link #configure(SecurityBuilder)} method uses
	 * the correct shared objects when building. Configurers should be applied here.
	 * 初始化SecurityBuilder。在这里，应该只创建和修改共享状态，而不应该创建和修改用于构建对象的SecurityBuilder上的属性。
	 * 这样可以确保configure（SecurityBuilder）方法在构建时使用正确的共享对象。应在此处应用配置程序
	 * @param builder
	 * @throws Exception
	 * @笔者 入参传入一个SecurityBuilder&lt;O&gt;
	 */
	void init(B builder) throws Exception;

	/**
	 * Configure the {@link SecurityBuilder} by setting the necessary properties on the
	 * 通过在SecurityBuilder上设置必要的属性来配置SecurityBuilder
	 * {@link SecurityBuilder}.
	 * @param builder
	 * @throws Exception
	 */
	void configure(B builder) throws Exception;

}
