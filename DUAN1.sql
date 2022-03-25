USE [master]
GO
/****** Object:  Database [DUAN1]    Script Date: 25/03/2022 10:16:45 ******/
CREATE DATABASE [DUAN1]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DUAN1', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL15.VOVANDAI\MSSQL\DATA\DUAN1.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DUAN1_log', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL15.VOVANDAI\MSSQL\DATA\DUAN1_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DUAN1] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DUAN1].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DUAN1] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DUAN1] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DUAN1] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DUAN1] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DUAN1] SET ARITHABORT OFF 
GO
ALTER DATABASE [DUAN1] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DUAN1] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DUAN1] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DUAN1] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DUAN1] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DUAN1] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DUAN1] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DUAN1] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DUAN1] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DUAN1] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DUAN1] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DUAN1] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DUAN1] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DUAN1] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DUAN1] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DUAN1] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DUAN1] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DUAN1] SET RECOVERY FULL 
GO
ALTER DATABASE [DUAN1] SET  MULTI_USER 
GO
ALTER DATABASE [DUAN1] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DUAN1] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DUAN1] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DUAN1] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DUAN1] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DUAN1] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'DUAN1', N'ON'
GO
ALTER DATABASE [DUAN1] SET QUERY_STORE = OFF
GO
USE [DUAN1]
GO
/****** Object:  Table [dbo].[CHUYENDE]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHUYENDE](
	[MaCD] [nchar](7) NOT NULL,
	[TenCD] [nvarchar](50) NOT NULL,
	[HocPhi] [float] NOT NULL,
	[ThoiLuong] [int] NOT NULL,
	[Hinh] [image] NULL,
	[Mota] [nvarchar](max) NULL,
 CONSTRAINT [PK_CHUYENDE] PRIMARY KEY CLUSTERED 
(
	[MaCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HOCVIEN]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HOCVIEN](
	[MaHV] [int] IDENTITY(1,1) NOT NULL,
	[MaKH] [int] NOT NULL,
	[MaNH] [nchar](7) NOT NULL,
	[Diem] [float] NULL,
 CONSTRAINT [PK_HOCVIEN] PRIMARY KEY CLUSTERED 
(
	[MaHV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KHOAHOC]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KHOAHOC](
	[MaKH] [int] IDENTITY(1,1) NOT NULL,
	[MaCD] [nchar](7) NOT NULL,
	[HocPhi] [float] NOT NULL,
	[ThoiLuong] [int] NOT NULL,
	[NgayKG] [date] NOT NULL,
	[GhiChu] [nvarchar](255) NULL,
	[MaNV] [nchar](7) NOT NULL,
	[NgayTao] [date] NULL,
 CONSTRAINT [PK_KHOAHOC] PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NGUOIHOC]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NGUOIHOC](
	[MaNH] [nchar](7) NOT NULL,
	[HoVaTen] [nvarchar](50) NOT NULL,
	[GioiTinh] [bit] NULL,
	[NgaySinh] [date] NOT NULL,
	[SDT] [nvarchar](24) NOT NULL,
	[Hinh] [image] NULL,
	[Email] [nvarchar](50) NOT NULL,
	[GhiChu] [nvarchar](255) NULL,
	[MaNV] [nchar](7) NOT NULL,
	[NgayDK] [date] NULL,
 CONSTRAINT [PK_NGUOIHOC] PRIMARY KEY CLUSTERED 
(
	[MaNH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NHANVIEN]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NHANVIEN](
	[MaNV] [nchar](7) NOT NULL,
	[MatKhau] [nvarchar](50) NOT NULL,
	[HoVaTen] [nvarchar](50) NOT NULL,
	[VaiTro] [bit] NOT NULL,
 CONSTRAINT [PK_NHANVIEN] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HOCVIEN]  WITH CHECK ADD  CONSTRAINT [FK_HOCVIEN_KHOAHOC] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KHOAHOC] ([MaKH])
GO
ALTER TABLE [dbo].[HOCVIEN] CHECK CONSTRAINT [FK_HOCVIEN_KHOAHOC]
GO
ALTER TABLE [dbo].[HOCVIEN]  WITH CHECK ADD  CONSTRAINT [FK_HOCVIEN_NGUOIHOC] FOREIGN KEY([MaNH])
REFERENCES [dbo].[NGUOIHOC] ([MaNH])
GO
ALTER TABLE [dbo].[HOCVIEN] CHECK CONSTRAINT [FK_HOCVIEN_NGUOIHOC]
GO
ALTER TABLE [dbo].[KHOAHOC]  WITH CHECK ADD  CONSTRAINT [FK_KHOAHOC_CHUYENDE] FOREIGN KEY([MaCD])
REFERENCES [dbo].[CHUYENDE] ([MaCD])
GO
ALTER TABLE [dbo].[KHOAHOC] CHECK CONSTRAINT [FK_KHOAHOC_CHUYENDE]
GO
ALTER TABLE [dbo].[KHOAHOC]  WITH CHECK ADD  CONSTRAINT [FK_KHOAHOC_NHANVIEN] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NHANVIEN] ([MaNV])
GO
ALTER TABLE [dbo].[KHOAHOC] CHECK CONSTRAINT [FK_KHOAHOC_NHANVIEN]
GO
ALTER TABLE [dbo].[NGUOIHOC]  WITH CHECK ADD  CONSTRAINT [FK_NGUOIHOC_NHANVIEN] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NHANVIEN] ([MaNV])
GO
ALTER TABLE [dbo].[NGUOIHOC] CHECK CONSTRAINT [FK_NGUOIHOC_NHANVIEN]
GO
/****** Object:  StoredProcedure [dbo].[sp_BangDiem]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_BangDiem](@MaKH INT)
AS BEGIN
	SELECT
		nh.MaNH,
		nh.HoVaTen,
		hv.Diem
	FROM HocVien hv
		JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH
	WHERE hv.MaKH = @MaKH
	ORDER BY hv.Diem DESC
END
GO
/****** Object:  StoredProcedure [dbo].[sp_ThongKeDiem]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_ThongKeDiem]
AS BEGIN
	SELECT
		TenCD ChuyenDe,
		COUNT(MaHV) SoHV,
		MIN(Diem) ThapNhat,
		MAX(Diem) CaoNhat,
		AVG(Diem) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	GROUP BY TenCD
END
GO
/****** Object:  StoredProcedure [dbo].[sp_ThongKeDoanhThu]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_ThongKeDoanhThu](@Year INT)
AS BEGIN
	SELECT
		TenCD ChuyenDe,
		COUNT(DISTINCT kh.MaKH) SoKH,
		COUNT(hv.MaHV) SoHV,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	WHERE YEAR(NgayKG) = @Year
	GROUP BY TenCD
END

GO
/****** Object:  StoredProcedure [dbo].[sp_ThongKeNguoiHoc]    Script Date: 25/03/2022 10:16:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_ThongKeNguoiHoc]
AS BEGIN
	SELECT
		YEAR(NgayDK) Nam,
		COUNT(*) SoLuong,
		MIN(NgayDK) DauTien,
		MAX(NgayDK) CuoiCung
	FROM NguoiHoc
	GROUP BY YEAR(NgayDK)
END
GO
USE [master]
GO
ALTER DATABASE [DUAN1] SET  READ_WRITE 
GO
