if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_place_place]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[place] DROP CONSTRAINT FK_place_place
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_place_group_a]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[place_group] DROP CONSTRAINT FK_place_group_a
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_place_group_b]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[place_group] DROP CONSTRAINT FK_place_group_b
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_source_place]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[source] DROP CONSTRAINT FK_source_place
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_search_in_source_search]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[search_in_source] DROP CONSTRAINT FK_search_in_source_search
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_search_in_source_source]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[search_in_source] DROP CONSTRAINT FK_search_in_source_source
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_source_group_a]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[source_group] DROP CONSTRAINT FK_source_group_a
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_source_group_b]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[source_group] DROP CONSTRAINT FK_source_group_b
GO

/****** Object:  User Defined Function dbo.place_hier_name    Script Date: 8/4/2002 1:21:13 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_hier_name]') and xtype in (N'FN', N'IF', N'TF'))
drop function [dbo].[place_hier_name]
GO

/****** Object:  User Defined Function dbo.get_comment    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[get_comment]') and xtype in (N'FN', N'IF', N'TF'))
drop function [dbo].[get_comment]
GO

/****** Object:  View dbo.search_edit_c1    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[search_edit_c1]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[search_edit_c1]
GO

/****** Object:  View dbo.source_edit_c1    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source_edit_c1]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[source_edit_c1]
GO

/****** Object:  View dbo.source_edit_c2    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source_edit_c2]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[source_edit_c2]
GO

/****** Object:  View dbo.place_edit_c1    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_edit_c1]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[place_edit_c1]
GO

/****** Object:  View dbo.place_edit_c2    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_edit_c2]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[place_edit_c2]
GO

/****** Object:  View dbo.place_list    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_list]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[place_list]
GO

/****** Object:  View dbo.source_edit    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source_edit]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[source_edit]
GO

/****** Object:  View dbo.source_list    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source_list]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[source_list]
GO

/****** Object:  View dbo.place_edit    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_edit]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[place_edit]
GO

/****** Object:  View dbo.search_edit    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[search_edit]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[search_edit]
GO

/****** Object:  View dbo.fkey_table    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[fkey_table]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[fkey_table]
GO

/****** Object:  View dbo.search_list    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[search_list]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[search_list]
GO

/****** Object:  Table [dbo].[search_in_source]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[search_in_source]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[search_in_source]
GO

/****** Object:  Table [dbo].[source_group]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source_group]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[source_group]
GO

/****** Object:  Table [dbo].[place_group]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place_group]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[place_group]
GO

/****** Object:  Table [dbo].[source]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[source]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[source]
GO

/****** Object:  Table [dbo].[gedcom]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[gedcom]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[gedcom]
GO

/****** Object:  Table [dbo].[jpeg]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[jpeg]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[jpeg]
GO

/****** Object:  Table [dbo].[place]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[place]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[place]
GO

/****** Object:  Table [dbo].[search]    Script Date: 8/4/2002 1:21:14 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[search]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[search]
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  User Defined Function dbo.get_comment    Script Date: 8/4/2002 1:21:21 AM ******/

CREATE function get_comment(@sObjText varchar(4000) = '')
returns varchar(4000)
as
begin
	declare @istart int
	declare @ilength int

	set @istart = charindex('/*',@sObjText)+2
	set @ilength = charindex('*/',@sObjText)-@istart
	if @ilength < 0
	begin
		set @ilength = 0
	end

	return substring(@sObjText,@istart,@ilength)
end




GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  User Defined Function dbo.place_hier_name    Script Date: 8/4/2002 1:21:21 AM ******/
CREATE function place_hier_name(@pk uniqueidentifier)
returns varchar(4000)
as
begin
	declare @ret varchar(4000)
	declare @s varchar(4000)
	declare @par uniqueidentifier

	set @ret = ''
	while @pk is not null
	begin
		select
			@s = display_name,
			@par = display_parent_fk
		from
			dbo.place
		where
			pk = @pk
		if @s is not null
		begin
			if len(@ret)>0
			begin
				set @ret = @ret+', '
			end
			set @ret = @ret+@s
		end
		set @pk = null
		set @pk = @par
	end

	return @ret
end



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  Table [dbo].[gedcom]    Script Date: 8/4/2002 1:21:16 AM ******/
CREATE TABLE [dbo].[gedcom] (
	[Col001] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[jpeg]    Script Date: 8/4/2002 1:21:17 AM ******/
CREATE TABLE [dbo].[jpeg] (
	[pk]  uniqueidentifier ROWGUIDCOL  NOT NULL ,
	[name] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[jpeg] [image] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[place]    Script Date: 8/4/2002 1:21:18 AM ******/
CREATE TABLE [dbo].[place] (
	[pk]  uniqueidentifier ROWGUIDCOL  NOT NULL ,
	[display_name] [varchar] (256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[full_name] [varchar] (256) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[display_parent_fk] [uniqueidentifier] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[search]    Script Date: 8/4/2002 1:21:18 AM ******/
CREATE TABLE [dbo].[search] (
	[pk] [uniqueidentifier] NOT NULL ,
	[description] [varchar] (64) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[full_description] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[place_group]    Script Date: 8/4/2002 1:21:19 AM ******/
CREATE TABLE [dbo].[place_group] (
	[a_fk] [uniqueidentifier] NOT NULL ,
	[b_fk] [uniqueidentifier] NOT NULL ,
	[a_rel_b] [varchar] (256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[source]    Script Date: 8/4/2002 1:21:19 AM ******/
CREATE TABLE [dbo].[source] (
	[pk]  uniqueidentifier ROWGUIDCOL  NOT NULL ,
	[author] [varchar] (64) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[title] [varchar] (128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[place_fk] [uniqueidentifier] NULL ,
	[publisher] [varchar] (32) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[written] [uniqueidentifier] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[search_in_source]    Script Date: 8/4/2002 1:21:20 AM ******/
CREATE TABLE [dbo].[search_in_source] (
	[search_fk] [uniqueidentifier] NOT NULL ,
	[source_fk] [uniqueidentifier] NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[source_group]    Script Date: 8/4/2002 1:21:20 AM ******/
CREATE TABLE [dbo].[source_group] (
	[a_fk] [uniqueidentifier] NOT NULL ,
	[b_fk] [uniqueidentifier] NOT NULL ,
	[a_rel_b] [varchar] (32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[jpeg] WITH NOCHECK ADD 
	CONSTRAINT [DF_jpeg_pk] DEFAULT (newid()) FOR [pk]
GO

 CREATE  UNIQUE  INDEX [IX_jpeg] ON [dbo].[jpeg]([pk]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [IX_jpeg_name] ON [dbo].[jpeg]([name]) ON [PRIMARY]
GO

ALTER TABLE [dbo].[place] WITH NOCHECK ADD 
	CONSTRAINT [DF_place_pk] DEFAULT (newid()) FOR [pk]
GO

 CREATE  INDEX [IX_place_display_name] ON [dbo].[place]([display_name]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [IX_place] ON [dbo].[place]([pk]) ON [PRIMARY]
GO

ALTER TABLE [dbo].[search] WITH NOCHECK ADD 
	CONSTRAINT [DF_search_search_pk] DEFAULT (newid()) FOR [pk]
GO

 CREATE  UNIQUE  INDEX [IX_search] ON [dbo].[search]([pk]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [IX_search_description] ON [dbo].[search]([description]) ON [PRIMARY]
GO

ALTER TABLE [dbo].[source] WITH NOCHECK ADD 
	CONSTRAINT [DF_source_pk] DEFAULT (newid()) FOR [pk]
GO

 CREATE  UNIQUE  INDEX [IX_source] ON [dbo].[source]([pk]) ON [PRIMARY]
GO

 CREATE  INDEX [IX_source_author] ON [dbo].[source]([author]) ON [PRIMARY]
GO

 CREATE  INDEX [IX_source_title] ON [dbo].[source]([title]) ON [PRIMARY]
GO

ALTER TABLE [dbo].[source_group] WITH NOCHECK ADD 
	CONSTRAINT [DF_source_group_a_fk] DEFAULT (newid()) FOR [a_fk]
GO

ALTER TABLE [dbo].[place] ADD 
	CONSTRAINT [FK_place_place] FOREIGN KEY 
	(
		[display_parent_fk]
	) REFERENCES [dbo].[place] (
		[pk]
	)
GO

ALTER TABLE [dbo].[place_group] ADD 
	CONSTRAINT [FK_place_group_a] FOREIGN KEY 
	(
		[a_fk]
	) REFERENCES [dbo].[place] (
		[pk]
	),
	CONSTRAINT [FK_place_group_b] FOREIGN KEY 
	(
		[b_fk]
	) REFERENCES [dbo].[place] (
		[pk]
	)
GO

ALTER TABLE [dbo].[source] ADD 
	CONSTRAINT [FK_source_place] FOREIGN KEY 
	(
		[place_fk]
	) REFERENCES [dbo].[place] (
		[pk]
	)
GO

ALTER TABLE [dbo].[search_in_source] ADD 
	CONSTRAINT [FK_search_in_source_search] FOREIGN KEY 
	(
		[search_fk]
	) REFERENCES [dbo].[search] (
		[pk]
	),
	CONSTRAINT [FK_search_in_source_source] FOREIGN KEY 
	(
		[source_fk]
	) REFERENCES [dbo].[source] (
		[pk]
	)
GO

ALTER TABLE [dbo].[source_group] ADD 
	CONSTRAINT [FK_source_group_a] FOREIGN KEY 
	(
		[a_fk]
	) REFERENCES [dbo].[source] (
		[pk]
	),
	CONSTRAINT [FK_source_group_b] FOREIGN KEY 
	(
		[b_fk]
	) REFERENCES [dbo].[source] (
		[pk]
	)
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.fkey_table    Script Date: 8/4/2002 1:21:20 AM ******/
CREATE view fkey_table as
select o.name table_name, c.name column_name, o2.name fk_table_name from
sysobjects o
inner join sysforeignkeys pt on (o.id=pt.fkeyid)
inner join syscolumns c on (c.colid = pt.fkey and c.id = o.id)
inner join sysobjects o2 on (o2.id = pt.rkeyid)
where o.type = 'U'

GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.search_list    Script Date: 8/4/2002 1:21:21 AM ******/

CREATE VIEW dbo.search_list
AS
SELECT     pk, description AS [Search Description]
FROM         dbo.search


GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.place_edit    Script Date: 8/4/2002 1:21:21 AM ******/
CREATE view place_edit as
select * from place

GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.search_edit    Script Date: 8/4/2002 1:21:21 AM ******/


CREATE  view search_edit as
select * from search



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.place_edit_c1    Script Date: 8/4/2002 1:21:21 AM ******/
/*<b>places that depend on [this] place:</b>*/
CREATE  VIEW dbo.place_edit_c1
AS
SELECT     pa.pk AS pk, g.b_fk AS fk, pa.display_name, g.a_rel_b AS rel, '[this]' AS this
FROM         dbo.place pa INNER JOIN
                      dbo.place_group g ON pa.pk = g.a_fk INNER JOIN
                      dbo.place pb ON pb.pk = g.b_fk



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.place_edit_c2    Script Date: 8/4/2002 1:21:21 AM ******/
/*<b>places that [this] place depends on:</b>*/
CREATE  VIEW dbo.place_edit_c2
AS
SELECT     pa.pk AS fk, g.b_fk AS pk, '[this]' AS this, g.a_rel_b AS rel, pb.display_name
FROM         dbo.place pa INNER JOIN
                      dbo.place_group g ON pa.pk = g.a_fk INNER JOIN
                      dbo.place pb ON pb.pk = g.b_fk



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.place_list    Script Date: 8/4/2002 1:21:21 AM ******/

CREATE  VIEW dbo.place_list
AS
SELECT     pk, full_name as [Full Name], dbo.place_hier_name(pk) AS [Hierarchical Name]
FROM         dbo.place



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.source_edit    Script Date: 8/4/2002 1:21:21 AM ******/

create view source_edit as
select * from source


GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.source_list    Script Date: 8/4/2002 1:21:21 AM ******/

CREATE VIEW dbo.source_list
AS
SELECT     pk, title AS [Title], author as [Author]
FROM         dbo.source


GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.search_edit_c1    Script Date: 8/4/2002 1:21:21 AM ******/
/*<b>sources for search:</b>*/
CREATE  VIEW dbo.search_edit_c1
AS
SELECT     source.pk AS pk, search_in_source.search_fk AS fk, source.title
FROM       dbo.search INNER JOIN
           dbo.search_in_source ON search.pk = search_in_source.search_fk INNER JOIN
           dbo.source ON source.pk = search_in_source.source_fk



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.source_edit_c1    Script Date: 8/4/2002 1:21:21 AM ******/
/*<b>sources that depend on [this] sources:</b>*/
CREATE VIEW dbo.source_edit_c1
AS
SELECT     g.a_fk AS pk, pb.pk AS fk, pa.title, g.a_rel_b AS rel, '[this]' AS this
FROM         dbo.source pa INNER JOIN
                      dbo.source_group g ON pa.pk = g.a_fk INNER JOIN
                      dbo.source pb ON pb.pk = g.b_fk



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

/****** Object:  View dbo.source_edit_c2    Script Date: 8/4/2002 1:21:21 AM ******/
/*<b>sources that [this] sources depends on:</b>*/
CREATE VIEW dbo.source_edit_c2
AS
SELECT     pa.pk AS fk, g.b_fk AS pk, '[this]' AS this, g.a_rel_b AS rel, pb.title
FROM         dbo.source pa INNER JOIN
                      dbo.source_group g ON pa.pk = g.a_fk INNER JOIN
                      dbo.source pb ON pb.pk = g.b_fk




GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO


exec sp_addextendedproperty N'MS_Description', N'Each row represents a place: either a city, a building, a county, a country, or any other physical location in space. Places can be grouped together by the place_group table.', N'user', N'dbo', N'table', N'place'


GO

